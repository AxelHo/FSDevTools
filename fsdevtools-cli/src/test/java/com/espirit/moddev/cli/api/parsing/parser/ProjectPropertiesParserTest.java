/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espirit.moddev.cli.api.parsing.parser;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.espirit.moddev.cli.api.parsing.identifier.ProjectPropertiesIdentifier;

import de.espirit.firstspirit.transport.PropertiesTransportOptions;

/**
 *
 * @author kohlbrecher
 */
@RunWith(Theories.class)
public class ProjectPropertiesParserTest {
    @DataPoints
    public static List[] testcases =
            new List[]{ Arrays.asList("Projectproperty:LANGUAGES"),
                    Arrays.asList("projectproperty:languages"),
                    Arrays.asList("projectproperty :LANGUAGES"),
                    Arrays.asList("projectproperty : LANGUAGES")};

    private ProjectPropertiesParser testling;

    @Before
    public void setUp() {
        testling = new ProjectPropertiesParser();
    }


    @Theory
    public void testAppliesTo(List<String> uids) throws Exception {
        for(String current : uids) {
            boolean appliesTo = testling.appliesTo(current);
            Assert.assertTrue("Parser should apply to string " + current, appliesTo);
        }
    }

    @Test
    public void parse() throws Exception {
        List<ProjectPropertiesIdentifier> result = testling.parse(Arrays.asList("projectproperty:LANGUAGES"));
        Assert.assertEquals(1, result.size());
        EnumSet<PropertiesTransportOptions.ProjectPropertyType> enumSet = EnumSet.noneOf(PropertiesTransportOptions.ProjectPropertyType.class);
        enumSet.add(PropertiesTransportOptions.ProjectPropertyType.LANGUAGES);
        Assert.assertEquals(new ProjectPropertiesIdentifier(enumSet), result.get(0));
    }

    @Test
    public void testAppliesTo() throws Exception {
        Assert.assertTrue(testling.appliesTo("projectproperty:languages"));
    }
    @Test
    public void testDontApplyTo() throws Exception {
        Assert.assertFalse(testling.appliesTo("asdasd"));
    }
    @Test
    public void testDontApplyToStartsWithEntitiesIdentifier() throws Exception {
        Assert.assertFalse(testling.appliesTo("entitiesaasd:asd"));
    }
}