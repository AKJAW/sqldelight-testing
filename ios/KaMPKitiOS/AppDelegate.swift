//
//  AppDelegate.swift
//  KaMPKitiOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    var cancellables = Set<AnyCancellable>()
    
    lazy var viewModel: CommonItemsScreenViewModel =
    KoinWrapper.get(type: CommonItemsScreenViewModel.self) as CommonItemsScreenViewModel

    func application(_ application: UIApplication, didFinishLaunchingWithOptions
        launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

        startKoin()
        
        #if DEBUG
        if CommandLine.arguments.contains("enable-testing") {
            SetUpKoinForTestingKt.setUpKoinForTesting()
            let mockTimeString = UserDefaults.standard.string(forKey: "mock-time")
            let mockTime = Int64(mockTimeString!)!
            print(mockTime)
            if mockTime != 0 {
                TestDoubleRetriever.shared.mockTimestampProvider.timestamp = Int64(mockTime)

            }
        }
        #endif
    
        let viewController = UIHostingController(rootView: MainScreen())

        self.window = UIWindow(frame: UIScreen.main.bounds)
        self.window?.rootViewController = viewController
        self.window?.makeKeyAndVisible()

        return true
    }
}
