import SwiftUI
import shared
import Combine
import KMPNativeCoroutinesCombine

class MainScreenPublisher: ObservableObject {
    private var cancellables = Set<AnyCancellable>()
    
    let viewModel: CommonItemsScreenViewModel =
    KoinWrapper.get(type: CommonItemsScreenViewModel.self) as CommonItemsScreenViewModel
    
    @Published
    var items: [TimestampItem] = []
    
    init() {
        createPublisher(for: viewModel.itemsNative).sink { completion in
            print("Received completion: \(completion)")
        } receiveValue: { value in
            self.items = value
            print("Received value: \(value)")
        }
            .store(in: &cancellables)
    }
    
    func addItem() {
        createFuture(for: self.viewModel.addItemNative())
            .sink { completion in
            print("Received completion: \(completion)")
        } receiveValue: { value in
            print("Received value: \(value)")
        }
        .store(in: &self.cancellables)
    }
    
    func updateItem(id: Int64) {
        createFuture(for: self.viewModel.updateItemNative(id: id))
            .sink { completion in
            print("Received completion: \(completion)")
        } receiveValue: { value in
            print("Received value: \(value)")
        }
        .store(in: &self.cancellables)
    }
}

struct MainScreen: View {
    
    @ObservedObject private var publisher = MainScreenPublisher()
    
    var body: some View {
        List {
            ForEach(
                publisher.items,
                id: \.self
            ) { item in
                HStack {
                    Text(item.name)
                    Spacer()
                    Image(systemName: "arrow.clockwise")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 20, height: 20)
                        .onTapGesture {
                            publisher.updateItem(id: item.id)
                        }
                }
            }
            HStack {
                Spacer()
                Text("Add")
                    .onTapGesture {
                        publisher.addItem()
                    }
                Spacer()
            }
        }
    }
}

struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainScreen()
    }
}
