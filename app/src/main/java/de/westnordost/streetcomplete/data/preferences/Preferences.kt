package de.westnordost.streetcomplete.data.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SettingsListener
import com.russhwolf.settings.boolean
import com.russhwolf.settings.float
import com.russhwolf.settings.int
import com.russhwolf.settings.long
import com.russhwolf.settings.nullableString
import com.russhwolf.settings.string
import de.westnordost.streetcomplete.ApplicationConstants.DEFAULT_AUTOSYNC
import de.westnordost.streetcomplete.ApplicationConstants.DEFAULT_RESURVEY_INTERVALS
import de.westnordost.streetcomplete.ApplicationConstants.DEFAULT_THEME
import de.westnordost.streetcomplete.data.osm.mapdata.LatLon

class Preferences(private val prefs: ObservableSettings) {
    // application settings
    var language: String? by prefs.nullableString(Prefs.LANGUAGE_SELECT)

    var theme: Theme
        set(value) { prefs.putString(Prefs.THEME_SELECT, value.name) }
        get() {
            val value = prefs.getStringOrNull(Prefs.THEME_SELECT)
            // AUTO setting was removed because as of June 2024, 95% of active installs from
            // google play use an Android where AUTO is deprecated
            return if (value == "AUTO" || value == null) Theme.SYSTEM else Theme.valueOf(value)
        }

    var autosync: Autosync
        set(value) { prefs.putString(Prefs.AUTOSYNC, value.name) }
        get() = Autosync.valueOf(prefs.getString(Prefs.AUTOSYNC, DEFAULT_AUTOSYNC))

    var keepScreenOn: Boolean by prefs.boolean(Prefs.KEEP_SCREEN_ON, false)

    var resurveyIntervals: ResurveyIntervals
        set(value) { prefs.putString(Prefs.RESURVEY_INTERVALS, value.name) }
        get() = ResurveyIntervals.valueOf(prefs.getString(Prefs.RESURVEY_INTERVALS, DEFAULT_RESURVEY_INTERVALS))

    var showAllNotes: Boolean by prefs.boolean(Prefs.SHOW_NOTES_NOT_PHRASED_AS_QUESTIONS, false)

    fun onLanguageChanged(callback: (String?) -> Unit): SettingsListener =
        prefs.addStringOrNullListener(Prefs.LANGUAGE_SELECT, callback)

    fun onThemeChanged(callback: (Theme) -> Unit): SettingsListener =
        prefs.addStringListener(Prefs.THEME_SELECT, DEFAULT_THEME) {
            callback(Theme.valueOf(it))
        }

    fun onAutosyncChanged(callback: (Autosync) -> Unit): SettingsListener =
        prefs.addStringListener(Prefs.AUTOSYNC, DEFAULT_AUTOSYNC) {
            callback(Autosync.valueOf(it))
        }

    fun onResurveyIntervalsChanged(callback: (ResurveyIntervals) -> Unit): SettingsListener =
        prefs.addStringListener(Prefs.RESURVEY_INTERVALS, DEFAULT_RESURVEY_INTERVALS) {
            callback(ResurveyIntervals.valueOf(it))
        }

    fun onAllShowNotesChanged(callback: (Boolean) -> Unit): SettingsListener =
        prefs.addBooleanListener(Prefs.SHOW_NOTES_NOT_PHRASED_AS_QUESTIONS, false, callback)

    fun onKeepScreenOnChanged(callback: (Boolean) -> Unit): SettingsListener =
        prefs.addBooleanListener(Prefs.KEEP_SCREEN_ON, false, callback)

    // login and user
    var userId: Long by prefs.long(Prefs.OSM_USER_ID, -1)
    var userName: String? by prefs.nullableString(Prefs.OSM_USER_NAME)
    var userUnreadMessages: Int by prefs.int(Prefs.OSM_UNREAD_MESSAGES, 0)

    var oAuth2AccessToken: String? by prefs.nullableString(Prefs.OAUTH2_ACCESS_TOKEN)
    val hasOAuth1AccessToken: Boolean get() = prefs.hasKey(Prefs.OAUTH1_ACCESS_TOKEN)

    fun clearUserData() {
        prefs.remove(Prefs.OSM_USER_ID)
        prefs.remove(Prefs.OSM_USER_NAME)
        prefs.remove(Prefs.OSM_UNREAD_MESSAGES)
    }

    fun removeOAuth1Data() {
        prefs.remove(Prefs.OAUTH1_ACCESS_TOKEN)
        prefs.remove(Prefs.OAUTH1_ACCESS_TOKEN_SECRET)
        prefs.remove(Prefs.OSM_LOGGED_IN_AFTER_OAUTH_FUCKUP)
    }

    // map state
    var mapPosition: LatLon
        set(value) {
            prefs.putDouble(Prefs.MAP_LATITUDE, value.latitude)
            prefs.putDouble(Prefs.MAP_LONGITUDE, value.longitude)
        }
        get() = LatLon(
            latitude = prefs.getDouble(Prefs.MAP_LATITUDE, 0.0),
            longitude = prefs.getDouble(Prefs.MAP_LONGITUDE, 0.0)
        )
    var mapRotation: Float by prefs.float(Prefs.MAP_ROTATION, 0f)
    var mapTilt: Float by prefs.float(Prefs.MAP_TILT, 0f)
    var mapZoom: Float by prefs.float(Prefs.MAP_ZOOM, 0f)
    var mapIsFollowing: Boolean by prefs.boolean(Prefs.MAP_FOLLOWING, true)
    var mapIsNavigationMode: Boolean by prefs.boolean(Prefs.MAP_NAVIGATION_MODE, false)

    // application version
    var lastChangelogVersion: String? by prefs.nullableString(Prefs.LAST_VERSION)
    var lastDataVersion: String? by prefs.nullableString(Prefs.LAST_VERSION_DATA)

    // team mode
    var teamModeSize: Int by prefs.int(Prefs.TEAM_MODE_TEAM_SIZE, -1)
    var teamModeIndexInTeam: Int by prefs.int(Prefs.TEAM_MODE_INDEX_IN_TEAM, -1)

    // tangram
    var pinSpritesVersion: Int by prefs.int(Prefs.PIN_SPRITES_VERSION, 0)
    var pinSprites: String by prefs.string(Prefs.PIN_SPRITES, "")

    var iconSpritesVersion: Int by prefs.int(Prefs.ICON_SPRITES_VERSION, 0)
    var iconSprites: String by prefs.string(Prefs.ICON_SPRITES, "")

    // main screen UI
    var hasShownTutorial: Boolean by prefs.boolean(Prefs.HAS_SHOWN_TUTORIAL, false)
    var hasShownOverlaysTutorial: Boolean by prefs.boolean(Prefs.HAS_SHOWN_OVERLAYS_TUTORIAL, false)

    // quest & overlay UI
    var preferredLanguageForNames: String? by prefs.nullableString(Prefs.PREFERRED_LANGUAGE_FOR_NAMES)

    // profile & statistics screen UI
    var userGlobalRank: Int by prefs.int(Prefs.USER_GLOBAL_RANK, -1)
    var userGlobalRankCurrentWeek: Int by prefs.int(Prefs.USER_GLOBAL_RANK_CURRENT_WEEK, -1)
    var userLastTimestampActive: Long by prefs.long(Prefs.USER_LAST_TIMESTAMP_ACTIVE, 0)
    var userDaysActive: Int by prefs.int(Prefs.USER_DAYS_ACTIVE, 0)
    var userActiveDatesRange: Int by prefs.int(Prefs.ACTIVE_DATES_RANGE, 100)

    // default true because if it is not set yet, the first thing that is done is to synchronize it
    var isSynchronizingStatistics: Boolean by prefs.boolean(Prefs.IS_SYNCHRONIZING_STATISTICS, true)

    fun clearUserStatistics() {
        prefs.remove(Prefs.USER_DAYS_ACTIVE)
        prefs.remove(Prefs.ACTIVE_DATES_RANGE)
        prefs.remove(Prefs.IS_SYNCHRONIZING_STATISTICS)
        prefs.remove(Prefs.USER_GLOBAL_RANK)
        prefs.remove(Prefs.USER_GLOBAL_RANK_CURRENT_WEEK)
        prefs.remove(Prefs.USER_LAST_TIMESTAMP_ACTIVE)
    }
}
