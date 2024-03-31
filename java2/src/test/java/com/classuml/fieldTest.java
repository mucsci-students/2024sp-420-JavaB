package com.classuml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.classuml.Model.*;
import static org.junit.jupiter.api.Assertions.*;

public class fieldTest {

    private Field field;

    @BeforeEach
    public void setUp() {
        field = new Field("fieldName", "String");
    }

    @Test
    public void testToString() {
        assertEquals("\n    Name: fieldName,\n    Type: String", field.toString());
    }
}