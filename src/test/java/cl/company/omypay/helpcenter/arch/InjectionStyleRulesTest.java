package cl.company.omypay.helpcenter.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

public class InjectionStyleRulesTest {

    private JavaClasses importedClasses;

    @BeforeEach
    void setUp() {
        importedClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("cl.company.omypay.helpcenter");
    }

    @Test
    void fieldInjectionShouldNotBeUsed() {
        noFields()
                .should().beAnnotatedWith(Inject.class)
                .check(importedClasses);
    }

}
