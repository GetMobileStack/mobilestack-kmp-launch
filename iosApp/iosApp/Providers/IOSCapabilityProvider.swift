import Foundation
import SwiftUI
import shared

class IOSCapabilityProvider: OSCapabilityProvider {
    func openUrl(url: String) {
        guard let parsedUrl = URL(string: url) else {
            print("IOSCapabilityProvider/openUrl: Invalid URL: \(url)")
            return
        }
        UIApplication.shared.open(parsedUrl)
    }
    
    func getPlatform() -> OSCapabilityProviderPlatform {
        return OSCapabilityProviderPlatform.ios
    }
    
    func getAppVersion() -> String {
        return Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String ?? "0.0.0"
    }
    
    func managePurchases(){
        openUrl(url: "https://apps.apple.com/account/subscriptions")
    }
}
