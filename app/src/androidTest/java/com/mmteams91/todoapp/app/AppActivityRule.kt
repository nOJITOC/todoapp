package com.mmteams91.todoapp.app

import android.support.test.rule.ActivityTestRule
import com.mmteams91.todoapp.app.presentation.AppActivity

class AppActivityRule:ActivityTestRule<AppActivity>(AppActivity::class.java,true,true)