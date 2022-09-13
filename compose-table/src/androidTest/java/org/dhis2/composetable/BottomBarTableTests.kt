package org.dhis2.composetable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.dhis2.composetable.activity.TableTestActivity
import org.dhis2.composetable.data.tableData
import org.dhis2.composetable.model.FakeTableModels
import org.dhis2.composetable.model.TableCell
import org.dhis2.composetable.model.TableModel
import org.dhis2.composetable.model.TextInputModel
import org.dhis2.composetable.ui.DataTable
import org.dhis2.composetable.ui.TableColors
import org.dhis2.composetable.ui.TableSelection
import org.dhis2.composetable.ui.TextInput
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class BottomBarTableTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TableTestActivity>()
    private val tableColors = TableColors()

    @Test
    fun shouldDisplayBottomBarComponentWhenTyping() {
        val fakeModel = FakeTableModels(
            context = composeTestRule.activity.applicationContext
        ).getMultiHeaderTables()

        var cellToSave: TableCell? = null
        val tableId = fakeModel[0].id

        composeTestRule.setContent {
            TextInputUiTestScreen(fakeModel){
                cellToSave = it
            }
        }

        tableRobot(composeTestRule) {
            clickOnCell(tableId!!, 1, 0)
            typeInput("test")
            assertBottomBarIsVisible()
        }
    }

    @Test
    @Ignore
    fun shouldTheElementWrittenInBottomBarBeTheSameInCell() {
        val fakeModel = FakeTableModels(
            context = composeTestRule.activity.applicationContext
        ).getMultiHeaderTables()

        var cellToSave: TableCell? = null
        val tableId = fakeModel[0].id

        composeTestRule.setContent {
            TextInputUiTestScreen(fakeModel){
                cellToSave = it
            }
        }

        tableRobot(composeTestRule) {
            clickOnCell(tableId!!, 1, 0)
            composeTestRule.waitForIdle()
            typeInput("test")
            composeTestRule.waitForIdle()
        }

        Thread.sleep(10000)
    }

    @Test
    fun shouldAssertBottomBarStateBeforeAndAfterTyping() {
        val fakeModel = FakeTableModels(
            context = composeTestRule.activity.applicationContext
        ).getMultiHeaderTables()

        var cellToSave: TableCell? = null
        val tableId = fakeModel[0].id

        composeTestRule.setContent {
            TextInputUiTestScreen(fakeModel){
                cellToSave = it
            }
        }

        tableRobot(composeTestRule) {
            clickOnCell(tableId!!, 1, 0)
            typeInput("test")
            clickOnAccept()
            // check table cell value
            // check keyboard is closed
            // check assert to pencil
        }
    }

    @Test
    fun shouldClickOnNextAndSavedValue() {
        val fakeModel = FakeTableModels(
            context = composeTestRule.activity.applicationContext
        ).getMultiHeaderTables()

        var cellToSave: TableCell? = null
        val tableId = fakeModel[0].id

        composeTestRule.setContent {
            TextInputUiTestScreen(fakeModel){
                cellToSave = it
            }
        }

        
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun TextInputUiTestScreen(fakeTableModels: List<TableModel>, onSave: (TableCell) -> Unit) {
        val bottomSheetState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        )
        var currentCell by remember {
            mutableStateOf<TableCell?>(
                null
            )
        }
        var currentInputType by remember {
            mutableStateOf(
                TextInputModel()
            )
        }

        var tableSelection by remember {
            mutableStateOf<TableSelection>(TableSelection.Unselected())
        }

        val coroutineScope = rememberCoroutineScope()

        BottomSheetScaffold(
            scaffoldState = bottomSheetState,
            sheetContent = {
                TextInput(
                    textInputModel = currentInputType,
                    onTextChanged = { textInputModel ->
                        currentInputType = textInputModel
                        currentCell = currentCell?.copy(
                            value = textInputModel.currentValue,
                            error = null
                        )
                    },
                    onSave = { currentCell?.let { onSave(it) } },
                    onNextSelected = {}
                )
            },
            sheetPeekHeight = 0.dp,
            sheetShape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp
            )
        ) {
            DataTable(
                tableList = fakeTableModels,
                tableColors = TableColors(
                    primary = tableColors.primary,
                    primaryLight = tableColors.primaryLight
                ),
                tableSelection = tableSelection,
                onSelectionChange = { tableSelection = it },
                onDecorationClick = {

                }
            ) { cell ->
                currentCell = cell
                currentInputType = TextInputModel(
                    id = cell.id!!,
                    mainLabel = "Main Label",
                    secondaryLabels = listOf("Second Label 1", "Second Label 2"),
                    cell.value
                )
                coroutineScope.launch {
                    if (bottomSheetState.bottomSheetState.isCollapsed) {
                        bottomSheetState.bottomSheetState.expand()
                    }
                }
            }
        }
    }

 /*   @OptIn(ExperimentalMaterialApi::class)
    private fun initTable(fakeModel: List<TableModel>) {
        var cellToSave: TableCell? = null
        val expectedValue = "55"

        composeTestRule.setContent {

            val bottomSheetState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
            )
            var currentCell by remember {
                mutableStateOf<TableCell?>(
                    null
                )
            }
            var currentInputType by remember {
                mutableStateOf(
                    TextInputModel()
                )
            }

            var tableSelection by remember {
                mutableStateOf<TableSelection>(TableSelection.Unselected())
            }

            val coroutineScope = rememberCoroutineScope()

            BottomSheetScaffold(
                scaffoldState = bottomSheetState,
                sheetContent = {
                    TextInput(
                        textInputModel = currentInputType,
                        onTextChanged = { textInputModel ->
                            currentInputType = textInputModel
                            currentCell = currentCell?.copy(
                                value = textInputModel.currentValue,
                                error = null
                            )
                        },
                        onSave = {},
                        onNextSelected = {}
                    )
                },
                sheetPeekHeight = 0.dp,
                sheetShape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            ) {
                DataTable(
                    tableList = fakeModel,
                    tableColors = TableColors(
                        primary = MaterialTheme.colors.primary,
                        primaryLight = MaterialTheme.colors.primary.copy(alpha = 0.2f)
                    ),
                    tableSelection = tableSelection,
                    onSelectionChange = { tableSelection = it },
                    onDecorationClick = {

                    }
                ) { cell ->
                    currentCell = cell
                    currentInputType = TextInputModel(
                        id = cell.id!!,
                        mainLabel = "Main Label",
                        secondaryLabels = listOf("Second Label 1", "Second Label 2"),
                        cell.value
                    )
                    coroutineScope.launch {
                        if (bottomSheetState.bottomSheetState.isCollapsed) {
                            bottomSheetState.bottomSheetState.expand()
                        }
                    }
                }
            }
        }
    } */
}