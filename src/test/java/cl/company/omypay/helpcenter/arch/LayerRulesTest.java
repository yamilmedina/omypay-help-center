package cl.company.omypay.helpcenter.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class LayerRulesTest {

    private JavaClasses importedClasses;

    @BeforeEach
    void setUp() {
        importedClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("cl.company.omypay.helpcenter");
    }

    @Test
    void domainClassesShouldNotAccessApplicationOrInfrastructurePackages() {
        noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .accessClassesThat()
                .resideInAnyPackage("..infrastructure..",
                        "..application..",
                        "cl.company.omypay.helpcenter.shared..")
                .check(importedClasses);
    }

    @Test
    void serviceClassesShouldNotDependOnWebLayer() {
        noClasses()
                .that()
                .resideInAPackage("..application..")
                .should()
                .accessClassesThat()
                .resideInAnyPackage("..infrastructure.web..")
                .check(importedClasses);
    }


}
