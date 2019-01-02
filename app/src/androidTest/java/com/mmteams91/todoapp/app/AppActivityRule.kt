package com.mmteams91.todoapp.app

import android.support.test.rule.ActivityTestRule

class AppActivityRule:ActivityTestRule<AppActivity>(AppActivity::class.java,true,true)