import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.*;
import com.structurizr.view.*;

public class FinancialRiskSystem {

    private static final String API_KEY = "key";
    private static final String API_SECRET = "secret";
    private static final long WORKSPACE_ID = 3621;

    public static void main(String[] args) throws Exception {
        Workspace workspace = new Workspace("Financial Risk System", "This workspace contains example system context diagrams showing some possible solutions to the Financial Risk System architecture kata.");
        Model model = workspace.getModel();
        ViewSet viewSet = workspace.getViews();
        Styles styles = viewSet.getConfiguration().getStyles();

        SoftwareSystem financialRiskSystem = model.addSoftwareSystem("Financial Risk System", "Calculates the bank's exposure to risk for product X.");
        financialRiskSystem.addTags("Financial Risk System");

        Person businessUser = model.addPerson("Business User", "A regular business user.");
        Relationship businessUserViewsReports = businessUser.uses(financialRiskSystem, "Views reports using");

        Person configurationUser = model.addPerson("Configuration User", "A regular business user who can also configure the parameters used in the risk calculations.");
        Relationship configurationUserUsesFinancialRiskSystem = configurationUser.uses(financialRiskSystem, "Configures parameters using");

        SoftwareSystem tradeDataSystem = model.addSoftwareSystem("Trade Data System", "The system of record for trades of type X.");
        Relationship financialRiskSystemUsesTradeDataSystem = financialRiskSystem.uses(tradeDataSystem, "Gets trade data from");

        SoftwareSystem referenceDataSystem = model.addSoftwareSystem("Reference Data System", "Manages reference data for all counterparties the bank interacts with.");
        Relationship financialRiskSystemUsesReferenceDataSystem = financialRiskSystem.uses(referenceDataSystem, "Gets counterparty data from");

        SoftwareSystem emailSystem = model.addSoftwareSystem("E-mail system", "The corporate Microsoft Exchange e-mail system.");
        Relationship financialRiskSystemUsesEmailSystem = financialRiskSystem.uses(emailSystem, "Sends a notification that a report is ready using");
        Relationship emailSystemDeliversEmailToBusinessUser = emailSystem.delivers(businessUser, "Sends a notification that a report is ready to");

        Relationship financialRiskSystemDeliversEmailToBusinessUser = financialRiskSystem.delivers(businessUser, "Sends a notification that a report is ready via e-mail to");

        SoftwareSystem corporateWiki = model.addSoftwareSystem("Corporate Wiki", "The corporate wiki used for sharing content and documents across the bank.");
        Relationship financialRiskSystemUsesCorporateWiki = financialRiskSystem.uses(corporateWiki, "Uploads the report to");
        Relationship businessUserUsesCorporateWiki = businessUser.uses(corporateWiki, "Views reports using");

        SoftwareSystem centralMonitoringService = model.addSoftwareSystem("Central Monitoring Service", "The bank-wide monitoring and alerting dashboard.");
        Relationship financialRiskSystemUsesCentralMonitoringService = financialRiskSystem.uses(centralMonitoringService, "Sends critical failure alerts to");

        SystemContextView systemContextView = viewSet.createSystemContextView(financialRiskSystem, "SystemContext", "");
        systemContextView.addAllElements();

        // the following elements and relationships are included in all examples
        financialRiskSystem.addTags("Example1", "Example2", "Example3", "Example4");
        businessUser.addTags("Example1", "Example2", "Example3", "Example4");
        configurationUser.addTags("Example1", "Example2", "Example3", "Example4");
        tradeDataSystem.addTags("Example1", "Example2", "Example3", "Example4");
        referenceDataSystem.addTags("Example1", "Example2", "Example3", "Example4");
        centralMonitoringService.addTags("Example1", "Example2", "Example3", "Example4");

        configurationUserUsesFinancialRiskSystem.addTags("Example1", "Example2", "Example3", "Example4");
        financialRiskSystemUsesTradeDataSystem.addTags("Example1", "Example2", "Example3", "Example4");
        financialRiskSystemUsesReferenceDataSystem.addTags("Example1", "Example2", "Example3", "Example4");
        financialRiskSystemUsesCentralMonitoringService.addTags("Example1", "Example2", "Example3", "Example4");

        // example 1
        viewSet.createFilteredView(systemContextView, "Example1", "Example 1: Reports are accessed via a file share.", FilterMode.Include, "Example1");
        businessUserViewsReports.addTags("Example1");

        // example 2
        viewSet.createFilteredView(systemContextView, "Example2", "Example 2: Report notifications are e-mailed to business users.", FilterMode.Include, "Example2");
        emailSystem.addTags("Example2");
        financialRiskSystemUsesEmailSystem.addTags("Example2");
        emailSystemDeliversEmailToBusinessUser.addTags("Example2");
        businessUserViewsReports.addTags("Example2");

        // example 3
        viewSet.createFilteredView(systemContextView, "Example3", "Example 3: Report notifications are e-mailed to business users.", FilterMode.Include, "Example3");
        financialRiskSystemDeliversEmailToBusinessUser.addTags("Example3");
        businessUserViewsReports.addTags("Example3");

        // example 4
        viewSet.createFilteredView(systemContextView, "Example4", "Example 4: Reports are uploaded to the corporate wiki.", FilterMode.Include, "Example4");
        corporateWiki.addTags("Example4");
        financialRiskSystemUsesCorporateWiki.addTags("Example4");
        businessUserUsesCorporateWiki.addTags("Example4");

        // add some styling
        styles.addElementStyle(Tags.ELEMENT).width(600).height(450).fontSize(32).color("#ffffff");
        styles.addElementStyle("Financial Risk System").background("#02172c");
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").shape(Shape.RoundedBox);
        styles.addElementStyle(Tags.PERSON).width(550).background("#08427b").shape(Shape.Person);

        styles.addRelationshipStyle(Tags.RELATIONSHIP).thickness(5).fontSize(32).width(500);

        uploadWorkspaceToStructurizr(workspace);
    }

    private static void uploadWorkspaceToStructurizr(Workspace workspace) throws Exception {
        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);
    }

}