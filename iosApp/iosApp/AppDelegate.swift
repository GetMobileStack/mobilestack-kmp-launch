import Foundation
import SwiftUI
import shared
import FirebaseAuth
import FirebaseCrashlytics

class AppDelegate: NSObject, UIApplicationDelegate {
    let root: RootComponent = DefaultRootComponent(
        componentContext: DefaultComponentContext(lifecycle: ApplicationLifecycle()),
        osCapabilityProvider: IOSCapabilityProvider(),
        analyticsProvider: FirebaseAnalyticsProvider()
    )
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
#if DEBUG
        // Debug build
        
        // init napier
        NapierProxyKt.initializeDebug()
        
#else
        // Others(Release build)
        
        // init napier
        NapierProxyKt.initializeRelease(
            antilog: CrashlyticsAntilog(
                crashlyticsAddLog: { priority, tag, message in
                    Crashlytics.crashlytics().log("\(String(describing: tag)): \(String(describing: message))")
                },
                crashlyticsSendLog: { throwable in
                    Crashlytics.crashlytics().record(error: throwable.asError())
                }
            )
        )
#endif
        return true
    }
}
