package com.traininfo;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.traininfo")
class ArchitectureTest {

    @ArchTest
    static final ArchRule domain_is_free_of_framework_imports =
            noClasses().that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            "org.springframework..",
                            "jakarta.persistence..",
                            "com.fasterxml.jackson..");

    @ArchTest
    static final ArchRule controllers_reside_in_interfaces =
            noClasses().that().areMetaAnnotatedWith(Controller.class)
                    .should().resideOutsideOfPackage("..interfaces..");

    @ArchTest
    static final ArchRule layered_architecture =
            layeredArchitecture()
                    .consideringAllDependencies()
                    .layer("Interfaces").definedBy("com.traininfo.interfaces..")
                    .layer("Infrastructure").definedBy("com.traininfo.infrastructure..")
                    .layer("Domain").definedBy("com.traininfo.domain..")
                    .whereLayer("Interfaces").mayNotBeAccessedByAnyLayer()
                    .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
                    .whereLayer("Domain").mayOnlyBeAccessedByLayers("Interfaces", "Infrastructure");
}