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
    
        createPublisher(for: viewModel.itemsNative).sink { completion in
            print("Received completion: \(completion)")
        } receiveValue: { value in
            print("Received value: \(value)")
        }
            .store(in: &cancellables)

        let viewController = UIHostingController(rootView: Text("Initial").onTapGesture {
            createFuture(for: self.viewModel.addItemNative())
                .sink { completion in
                print("Received completion: \(completion)")
            } receiveValue: { value in
                print("Received value: \(value)")
            }
            .store(in: &self.cancellables)
         })

        self.window = UIWindow(frame: UIScreen.main.bounds)
        self.window?.rootViewController = viewController
        self.window?.makeKeyAndVisible()

        return true
    }
}
