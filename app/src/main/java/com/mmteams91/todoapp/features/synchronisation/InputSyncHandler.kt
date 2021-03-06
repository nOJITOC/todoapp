package com.mmteams91.todoapp.features.synchronisation

import android.content.SharedPreferences
import com.mmteams91.todoapp.common.data.database.AppDatabase
import com.mmteams91.todoapp.common.data.database.dao.BaseDao
import com.mmteams91.todoapp.common.data.database.records.BaseRecord
import com.mmteams91.todoapp.common.data.database.records.ItemRecord
import com.mmteams91.todoapp.common.data.database.records.ProjectRecord
import com.mmteams91.todoapp.common.entities.IBaseEntity
import com.mmteams91.todoapp.common.extensions.applyBoolean
import com.mmteams91.todoapp.common.extensions.applyString
import com.mmteams91.todoapp.common.extensions.safeSubscribe
import com.mmteams91.todoapp.common.transform.fieldparser.ItemFieldParser
import com.mmteams91.todoapp.common.transform.fieldparser.ProjectFieldParser
import com.mmteams91.todoapp.common.transform.transformer.SimpleTransformer
import com.mmteams91.todoapp.features.synchronisation.data.InputSyncCommand
import com.mmteams91.todoapp.features.synchronisation.data.InputSyncCommand.Types.SYNC_NEEDED
import com.mmteams91.todoapp.features.synchronisation.data.ResourceTypes.ITEMS
import com.mmteams91.todoapp.features.synchronisation.data.ResourceTypes.PROJECTS
import com.mmteams91.todoapp.features.synchronisation.data.SyncApi
import com.mmteams91.todoapp.features.synchronisation.data.models.ItemSm
import com.mmteams91.todoapp.features.synchronisation.data.models.ProjectSm
import com.mmteams91.todoapp.features.synchronisation.data.models.SyncResponse
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InputSyncHandler @Inject constructor(
        private val syncApi: SyncApi,
        private val sharedPreferences: SharedPreferences,
        override val scheduler: Scheduler,
        private val database: AppDatabase
) : ISyncHandler<InputSyncCommand> {
    private val itemTransformer = SimpleTransformer({ ItemRecord() }, ItemFieldParser())
    private val projectTransformer = SimpleTransformer({ ProjectRecord() }, ProjectFieldParser())
    override var disposables = CompositeDisposable()
    private var syncToken: String
        get() = sharedPreferences.getString(SYNC_TOKEN, DEFAULT_SYNC_TOKEN)!!
        set(value) = sharedPreferences.applyString(SYNC_TOKEN, value)
    private val resourceTypes: Set<String> = setOf(ITEMS, PROJECTS)

    override fun handle(commands: List<InputSyncCommand>) {
        //todo remove filter when add other command types
        commands.filter { it.type == SYNC_NEEDED }.forEach {
            disposables.add(handle(it).safeSubscribe { })
        }
    }

    private fun handle(command: InputSyncCommand): Completable {
        return when (command.type) {
            SYNC_NEEDED -> sync()
            else -> Completable.complete()
        }
    }

    private fun sync(): Completable {
        return syncApi.sync(syncToken, resourceTypes)
                .doOnSuccess { syncResponse ->
                    database.runInTransaction { saveEntities(syncResponse) }
                    syncToken = syncResponse.syncToken
                    if (syncResponse.fullSync) {
                        sharedPreferences.applyBoolean(IS_PRIMARY_SYNC_FINISHED, true)
                    }
                }.ignoreElement()
    }

    private fun saveEntities(syncResponse: SyncResponse) {
        syncResponse.projects?.let {
            saveEntities(it, database.getProjectDao(), projectTransformer)
        }
        syncResponse.items?.let {
            saveEntities(it, database.getItemDao(), itemTransformer)
        }
    }

    private fun <T : BaseRecord, Sm : IBaseEntity> saveEntities(entities: List<Sm>, dao: BaseDao<T>, transformer: SimpleTransformer<Sm, T>) {
        val inDb = dao.getByIds(entities.map { it.id }.toLongArray()).associateBy { it.id }
        val toInsert = mutableListOf<T>()
        val toUpdate = mutableListOf<T>()
        entities.forEach { entitySm ->
            val record = transformer.transform(entitySm)
            inDb[entitySm.id]?.let {
                record.localId = record.id
                toInsert.add(record)
            } ?: toUpdate.add(record)
        }
        if (toInsert.isNotEmpty()) dao.insert(toInsert)
        if (toUpdate.isNotEmpty()) dao.update(toUpdate)
    }


}

const val IS_PRIMARY_SYNC_FINISHED = "is_primary_sync_finished"
private const val SYNC_TOKEN = "sync_token"
private const val DEFAULT_SYNC_TOKEN = "*"