import XCTest
import shared
import KaMPKitiOS

class KaMPKitiOSUITests: XCTestCase {
    let app = XCUIApplication()

    func testAddingAnItemPopulatesTheListWithCurrentTimestamp() {
        app.launchArguments = ["enable-testing", "-mock-time", "1653823433000"]
        app.launch()
        
        app.staticTexts["Add"].tap()

        let itemExists = app.staticTexts["2022-05-29T11:23:53"].exists
        XCTAssert(itemExists)
    }
    
    func testRefreshingAnItemUpdatesTheNameWithCurrentTimestamp() {
        app.launchArguments = ["enable-testing", "-mock-time", "0,1653823433000"]
        app.launch()
        app.staticTexts["Add"].tap()
        
        app.buttons["Refresh"].tap()

        let itemExists = app.staticTexts["2022-05-29T11:23:53"].exists
        XCTAssert(itemExists)
    }
}
