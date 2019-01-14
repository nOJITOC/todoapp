package com.mmteams91.todoapp.features.tasklist.list

import com.mmteams91.todoapp.app.presentation.AppViewModel
import com.mmteams91.todoapp.common.presentation.ViewModelTestBasis
import io.reactivex.Flowable
import io.reactivex.observers.BaseTestConsumer
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.initMocks

class TaskListsViewModelTest : ViewModelTestBasis<TaskListsViewModel>() {

    private var data: List<TaskListRecord> = emptyList()
    lateinit var trackTaskListsUseCase: TrackTaskListsUseCase
    @Mock
    lateinit var taskListsRepository: ITaskListRepository
    @Mock
    lateinit var appViewModel: AppViewModel
    val transformer = TaskListFromRecordTransformer()
    @Before
    fun setUp() {
        initMocks(this)
        val flowable = Flowable.unsafeCreate<List<TaskListRecord>> { it.onNext(data) }
        `when`(taskListsRepository.getTaskLists()).thenReturn(flowable)
        trackTaskListsUseCase = TrackTaskListsUseCase(taskListsRepository, transformer)
        subject = TaskListsViewModel(trackTaskListsUseCase)


        subject.appViewModel = appViewModel
    }

    private fun givenTaskList(count: Int = 3) {
        data = if (count < 1) emptyList() else (1..count).map { TaskListRecord(id = "id_$it", title = "title $it") }
    }


    @Test
    fun `track taskLists - has data`() {
        givenTaskList()
        subject.onCreate()
        val subscriber = whenSubscribeToTaskLists()
        thenTaskListsSubscriberConsumeValues(subscriber)
    }

    @Test
    fun `track taskLists - no data`() {
        givenTaskList(0)
        subject.onCreate()
        val subscriber = whenSubscribeToTaskLists()
        thenTaskListsSubscriberConsumeValues(subscriber)
    }

    private fun thenTaskListsSubscriberConsumeValues(subscriber: TestSubscriber<List<TaskListVm>>) {
        subscriber.awaitCount(1, BaseTestConsumer.TestWaitStrategy.SLEEP_1MS, 1000)
        subscriber.assertValues(data.map { transformer.transform(it) })

    }

    private fun whenSubscribeToTaskLists(): TestSubscriber<List<TaskListVm>> {
        return subject.tasks().test()
    }

}