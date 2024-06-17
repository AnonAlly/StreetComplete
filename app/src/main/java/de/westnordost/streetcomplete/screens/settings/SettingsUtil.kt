package de.westnordost.streetcomplete.screens.settings

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import de.westnordost.streetcomplete.data.quest.QuestType

fun genericQuestTitle(resources: Resources, type: QuestType): String {
    // all parameters are replaced by generic three dots
    // it is assumed that quests will not have a ridiculously huge parameter count
    return resources.getString(type.title, *Array(10) { "…" })
}


@Composable
@ReadOnlyComposable
fun genericQuestTitle(type: QuestType): String {
    return stringResource(type.title, *Array(10) { "…" })
}
