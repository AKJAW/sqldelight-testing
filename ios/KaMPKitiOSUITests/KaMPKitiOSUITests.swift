import XCTest
import shared
import KaMPKitiOS

class KaMPKitiOSUITests: XCTestCase {
    let app = XCUIApplication()

    override func setUp() {
        app.launchArguments = ["enable-testing", "-mock-time", "1653823433000"]
        app.launch()
    }

    override func tearDown() {
    }

    func testAddingAnItemPopulatesTheListWithCurrentTimestamp() {
        app.staticTexts["Add"].tap()

        let itemExists = app.staticTexts["2022-05-29T11:23:53"].exists
        XCTAssert(itemExists)
    }
}
