package org.dhis2.composetable

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import org.dhis2.composetable.ui.CELL_TEST_TAG
import org.dhis2.composetable.ui.ColumnBackground
import org.dhis2.composetable.ui.ColumnIndexHeader
import org.dhis2.composetable.ui.DrawableId
import org.dhis2.composetable.ui.HEADER_CELL
import org.dhis2.composetable.ui.INFO_ICON
import org.dhis2.composetable.ui.INPUT_TEST_FIELD_TEST_TAG
import org.dhis2.composetable.ui.INPUT_TEST_TAG
import org.dhis2.composetable.ui.InfoIconId
import org.dhis2.composetable.ui.RowBackground
import org.dhis2.composetable.ui.RowIndex
import org.dhis2.composetable.ui.RowIndexHeader
import org.dhis2.composetable.ui.TableColors
import org.dhis2.composetable.ui.TableId
import org.dhis2.composetable.ui.TableIdColumnHeader

fun tableRobot(
    composeTestRule: ComposeContentTestRule,
    tableRobot: TableRobot.() -> Unit
) {
    TableRobot(composeTestRule).apply {
        tableRobot()
    }
}

class TableRobot(
    private val composeTestRule: ComposeContentTestRule,
) {

    fun assertClickOnCellShouldOpenInputComponent(rowIndex: Int, columnIndex: Int) {
        clickOnCell("table", rowIndex, columnIndex)
        assertInputComponentIsDisplayed()
    }

    fun assertClickOnEditOpensInputKeyboard() {
        clickOnEditValue()
        assertInputIcon(R.drawable.ic_finish_edit_input)
    }

    fun assertClickOnSaveHidesKeyboardAndSaveValue(valueToType: String) {
        clearInput()
        typeInput(valueToType)
        clickOnAccept()
    }

    fun assertInfoIcon(tableId: String, rowIndex: Int) {
        composeTestRule.onNode(
            SemanticsMatcher.expectValue(TableId, tableId)
                .and(SemanticsMatcher.expectValue(RowIndex, rowIndex))
                .and(SemanticsMatcher.expectValue(InfoIconId, INFO_ICON))
        ).assertExists()
    }

    fun assertRowHeaderBackgroundChangeToPrimary(
        tableId: String,
        rowIndex: Int,
        tableColors: TableColors
    ) {
        composeTestRule.onNode(
            SemanticsMatcher.expectValue(TableId, tableId)
                .and(SemanticsMatcher.expectValue(RowIndex, rowIndex))
                .and(SemanticsMatcher.expectValue(RowBackground, tableColors.primary))
        ).assertExists()
    }

    fun assertColumnHeaderBackgroundColor(
        tableId: String,
        rowIndex: Int,
        columnIndex: Int,
        color: Color
    ) {
        composeTestRule.onNode(
            SemanticsMatcher.expectValue(TableIdColumnHeader, tableId)
                .and(SemanticsMatcher.expectValue(RowIndexHeader, rowIndex))
                .and(SemanticsMatcher.expectValue(ColumnIndexHeader, columnIndex))
                .and(SemanticsMatcher.expectValue(ColumnBackground, color))
        ).assertExists()
    }

    fun clickOnCell(tableId: String, rowIndex: Int, columnIndex: Int) {
        composeTestRule.onNodeWithTag("$tableId${CELL_TEST_TAG}$rowIndex$columnIndex", true)
            .performClick()
    }

    fun clickOnHeaderElement(tableId: String, rowIndex: Int, columnIndex: Int) {
        composeTestRule.onNodeWithTag("$HEADER_CELL$tableId$rowIndex$columnIndex", true)
            .performClick()
        composeTestRule.waitForIdle()
    }

    fun clickOnRowHeader(tableId: String, rowIndex: Int) {
        composeTestRule.onNodeWithTag("$tableId$rowIndex").performClick()
        composeTestRule.waitForIdle()
    }

    fun assertRowHeaderText(tableId: String, text: String, rowIndex: Int) {
        composeTestRule.onNodeWithTag("${tableId}${rowIndex}").assertTextEquals(text)
    }

    fun assertRowHeaderIsClickable(tableId: String, text: String, rowIndex: Int) {
        composeTestRule.onNodeWithTag("$tableId$rowIndex").assertIsEnabled()
    }

    private fun clickOnEditValue() {
        composeTestRule.onNodeWithTag(INPUT_TEST_FIELD_TEST_TAG).performClick()
    }

    fun clearInput() {
        composeTestRule.onNodeWithTag(INPUT_TEST_FIELD_TEST_TAG).performTextClearance()
    }

    fun assertBottomBarIsVisible(){
        composeTestRule.onNodeWithTag(INPUT_TEST_FIELD_TEST_TAG).assertIsDisplayed()
    }

    fun typeInput(text: String) {
        composeTestRule.onNodeWithTag(INPUT_TEST_FIELD_TEST_TAG).performTextInput(text)
    }

    fun clickOnAccept() {
        composeTestRule.onNodeWithTag(INPUT_TEST_FIELD_TEST_TAG).performImeAction()
    }

    fun assertInputComponentIsDisplayed() {
        composeTestRule.onNodeWithTag(INPUT_TEST_TAG).assertIsDisplayed()
    }

    fun assertInputIcon(@DrawableRes id: Int) {
        composeTestRule.onNode(SemanticsMatcher.expectValue(DrawableId, id)).assertExists()
    }
}