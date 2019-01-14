package com.mmteams91.todoapp.features.user.auth

import com.mmteams91.todoapp.common.transform.transformer.Transformer
import com.mmteams91.todoapp.features.user.Features
import com.mmteams91.todoapp.features.user.TzInfo
import com.mmteams91.todoapp.features.user.User
import com.mmteams91.todoapp.features.user.data.FeaturesResponse
import com.mmteams91.todoapp.features.user.data.TzInfoResponse
import com.mmteams91.todoapp.features.user.data.UserResponse
import javax.inject.Inject

class UserFromResponseTransformer @Inject constructor(): Transformer<UserResponse, User> {
    private val tzInfoFromResponseTransformer = TzInfoFromResponseTransformer()
    private val featuresFromResponseTransformer = FeaturesFromResponseTransformer()

    override fun transform(from: UserResponse): User {
        return User(
                startPage = from.startPage,
                autoReminder = from.autoReminder,
                completedToday = from.completedToday,
                isPremium = from.isPremium,
                sortOrder = from.sortOrder,
                fullName = from.fullName,
                apiToken = from.apiToken,
                id = from.id,
                shareLimit = from.shareLimit,
                daysOff = from.daysOff,
                magicNumReached = from.magicNumReached,
                nextWeek = from.nextWeek,
                completedCount = from.completedCount,
                dailyGoal = from.dailyGoal,
                theme = from.theme,
                email = from.email,
                startDay = from.startDay,
                weeklyGoal = from.weeklyGoal,
                dateFormat = from.dateFormat,
                websocketUrl = from.websocketUrl,
                inboxProject = from.inboxProject,
                timeFormat = from.timeFormat,
                imageId = from.imageId,
                karmaTrend = from.karmaTrend,
                businessAccountId = from.businessAccountId,
                mobileNumber = from.mobileNumber,
                mobileHost = from.mobileHost,
                premiumUntil = from.premiumUntil,
                dateistLang = from.dateistLang,
                joinDate = from.joinDate,
                karma = from.karma,
                isBizAdmin = from.isBizAdmin,
                defaultReminder = from.defaultReminder,
                dateistInlineDisabled = from.dateistInlineDisabled,
                token = from.token,
                features = featuresFromResponseTransformer.transform(from.features),
                tzInfo = tzInfoFromResponseTransformer.transform(from.tzInfo)
        )
    }
}

private class FeaturesFromResponseTransformer : Transformer<FeaturesResponse, Features> {
    override fun transform(from: FeaturesResponse): Features {
        return Features(
                karmaDisabled = from.karmaDisabled,
                restriction = from.restriction,
                karmaVacation = from.karmaVacation,
                dateistLang = from.dateistLang,
                beta = from.beta,
                hasPushReminders = from.hasPushReminders,
                dateistInlineDisabled = from.dateistInlineDisabled
        )
    }
}

private class TzInfoFromResponseTransformer : Transformer<TzInfoResponse, TzInfo> {
    override fun transform(from: TzInfoResponse): TzInfo {
        return TzInfo(
                hours = from.hours,
                timezone = from.timezone,
                isDst = from.isDst,
                minutes = from.minutes,
                gmtString = from.gmtString
        )
    }
}