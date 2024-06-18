import SwiftUI
import shared

struct RootView: UIViewControllerRepresentable {
    let root: RootComponent
    
    func makeUIViewController(context: Context) -> UIViewController {
        let controller = ComposeUIKt.createRootViewController(component: root)
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
