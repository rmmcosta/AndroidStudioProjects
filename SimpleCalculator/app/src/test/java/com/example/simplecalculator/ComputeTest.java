package com.example.simplecalculator;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComputeTest {
    @Mock
    Context mockContext;

    @Test
    public void computeSum(){
        when(mockContext.getString(R.id.tvResult)).thenReturn("5");
        MainActivity mainActivity = new MainActivity();
    }
}
