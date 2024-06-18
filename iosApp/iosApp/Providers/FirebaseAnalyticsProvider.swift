import Foundation
import FirebaseAnalytics
import shared

class FirebaseAnalyticsProvider: AnalyticsProvider {
    func logEvent(eventName: String, screenName: String, params: [String : Any]) {
        let paramsWithScreen = params.merging([AnalyticsParameterScreenName: screenName]) { (_, new) in new }
        Analytics.logEvent(eventName, parameters: paramsWithScreen)
    }
}
