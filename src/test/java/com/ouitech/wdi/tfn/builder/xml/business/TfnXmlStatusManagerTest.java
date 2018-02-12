package com.ouitech.wdi.tfn.builder.xml.business;

import com.ouitech.wdi.tfn.builder.xml.TfnXmlLaunch;
import com.ouitech.wdi.tfn.builder.xml.input.TfnInputXml;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.Event;
import com.ouitech.wdi.tfn.builder.xml.output.surefire.TfnOutputXml;
import com.ouitech.wdi.tfn.common.TfnStateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.ouitech.wdi.tfn.builder.xml.business.TfnXmlStatusManager.*;
import static com.ouitech.wdi.tfn.common.TfnStateEnum.*;
import static java.util.Optional.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TfnXmlStatusManagerTest {

    @InjectMocks
    private TfnXmlStatusManager manager;

    private TfnInputXml input;
    private TfnOutputXml output;
    private TfnXmlLaunch launch;

    @Test
    public void should_be_INACTIVE_when_test_suite_disabled(){
        //Given
        input = TfnInputXml.builder().withTestSuiteDisabled(true).build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(INACTIVE);
    }

    @Test
    public void should_be_INACTIVE_when_test_case_disabled(){
        //Given
        input = TfnInputXml.builder().withDisabled(true).build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(INACTIVE);
    }

    @Test
    public void should_be_INACTIVE_when_property_fail_test_case_on_error_false(){
        //Given
        input = TfnInputXml.builder().withFailTestCaseOnErrors(false).build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(INACTIVE);
    }

    @Test
    public void should_be_INACTIVE_when_property_fail_error_false(){
        //Given
        input = TfnInputXml.builder().withFailOnError(false).build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(INACTIVE);
    }

    @Test
    public void should_be_SKIPPED_when_contains_event_type_skipped(){
        //Given
        given_tfn_input_active();
        output = TfnOutputXml.builder().withEvent(of(new Event(SKIPPED_EVENT))).build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(SKIPPED);
    }

    @Test
    public void should_be_ERROR_when_contains_event_type_error(){
        //Given
        given_tfn_input_active();
        output = TfnOutputXml.builder()
                .withEvent(of(new Event(ERROR_EVENT,
                        of("Validation Exception"),
                        of("This is a Validation Exception"))))
                .build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(ERROR);
    }

    @Test
    public void should_be_FAILED_when_contains_event_type_failed(){
        //Given
        given_tfn_input_active();
        output = TfnOutputXml.builder()
                .withEvent(of(new Event(FAILURE_EVENT,
                        of("Business Exception"),
                        of("This is a Business Exception"))))
                .build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(FAILED);
    }

    @Test
    public void should_be_SUCCESS(){
        //Given
        given_tfn_input_active();
        output = TfnOutputXml.builder().withEvent(empty()).build();
        given_tfn_launch();

        //When
        TfnStateEnum status = manager.define(launch);

        //Then
        assertThat(status).isEqualTo(SUCCESS);
    }

    private void given_tfn_input_active() {
        input = TfnInputXml.builder()
                .withTestSuiteDisabled(false)
                .withDisabled(false)
                .withFailTestCaseOnErrors(true)
                .withFailOnError(true)
                .build();
    }


    private void given_tfn_launch(){
        launch = new TfnXmlLaunch(input, ofNullable(output));
    }



}