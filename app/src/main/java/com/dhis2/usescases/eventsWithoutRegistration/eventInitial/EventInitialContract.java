package com.dhis2.usescases.eventsWithoutRegistration.eventInitial;

import android.app.DatePickerDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dhis2.data.forms.FormSectionViewModel;
import com.dhis2.data.forms.dataentry.fields.FieldViewModel;
import com.dhis2.usescases.general.AbstractActivityContracts;
import com.unnamed.b.atv.model.TreeNode;

import org.hisp.dhis.android.core.category.CategoryComboModel;
import org.hisp.dhis.android.core.category.CategoryOptionComboModel;
import org.hisp.dhis.android.core.event.EventModel;
import org.hisp.dhis.android.core.period.PeriodType;
import org.hisp.dhis.android.core.program.ProgramModel;
import org.hisp.dhis.android.core.program.ProgramStageModel;

import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Cristian on 01/03/2018.
 */

public class EventInitialContract {

    public interface View extends AbstractActivityContracts.View {
        void setProgram(@NonNull ProgramModel program);

        void setCatComboOptions(CategoryComboModel catCombo, List<CategoryOptionComboModel> catComboList);

        void showDateDialog(DatePickerDialog.OnDateSetListener listener);

        void openDrawer();

        void renderError(String message);

        void addTree(TreeNode treeNode);

        void setEvent(EventModel event);

        void setCatOption(CategoryOptionComboModel categoryOptionComboModel);

        void setLocation(double latitude, double longitude);

        void onEventCreated(String eventUid);

        void onEventUpdated(String eventUid);

        void setProgramStage(ProgramStageModel programStage);

        void onEventSections(List<FormSectionViewModel> formSectionViewModels);

        @NonNull
        Consumer<List<FieldViewModel>> showFields(String sectionUid);

        void showProgramStageSelection();

        void setReportDate(String format);
    }

    public interface Presenter extends AbstractActivityContracts.Presenter {
        void init(EventInitialContract.View view, String programId, String eventId);

        void getProgramStage(String programStageUid);

        void onBackClick();

        void createEvent(String enrollmentUid, String programStageModel, Date date, String orgUnitUid,
                         String catOption, String catOptionCombo,
                         String latitude, String longitude);

        void createEventPermanent(String enrollmentUid, String trackedEntityInstanceUid, String programStageModel,
                                  Date date, String orgUnitUid,
                                  String catOption, String catOptionCombo,
                                  String latitude, String longitude);

        void editEvent(String programStageModel, String eventUid, String date, String orgUnitUid,
                       String catOption, String catOptionCombo,
                       String latitude, String longitude);

        void onDateClick(@Nullable DatePickerDialog.OnDateSetListener listener);

        void onOrgUnitButtonClick();

        void onLocationClick();

        void onLocation2Click();

        void getCatOption(String categoryOptionComboId);

        void filterOrgUnits(String date);

        void getSectionCompletion(@Nullable String sectionUid);

        void onDetach();

        void goToSummary();

        void getEvents(String programUid, String enrollmentUid, String programStageUid, PeriodType periodType);

        void getOrgUnits();

        void getEventSections(@NonNull String eventId);


    }

    public interface Interactor extends AbstractActivityContracts.Interactor {

        void init(EventInitialContract.View view, String programId, String eventId);

        void getProgramStageWithId(String programStageUid);

        void getOrgUnits();

        void getCatOption(String categoryOptionComboId);

        void getFilteredOrgUnits(String date);

        void createNewEvent(String enrollmentUid, String programStageModel, String programUid, Date date, String orgUnitUid,
                            String categoryOptionComboUid, String categoryOptionsUid,
                            String latitude, String longitude);


        void createNewEventPermanent(String enrollmentUid, String trackedEntityInstanceUid, String programStageModel, String programUid, Date date, String orgUnitUid,
                                     String categoryOptionComboUid, String categoryOptionsUid,
                                     String latitude, String longitude);

        void editEvent(String programStageModelUid, String eventUid, String date, String orgUnitUid, String catComboUid, String catOptionCombo, String latitude, String longitude);

        void getEventSections(@NonNull String eventId);

        void getSectionCompletion(@Nullable String sectionUid);

        void onDetach();

        void getEvents(String programUid, String enrollmentUid, String programStageUid, PeriodType periodType);
    }
}
