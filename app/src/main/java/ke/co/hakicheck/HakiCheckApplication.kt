// HakiCheck Application Entry
package ke.co.hakicheck

import android.app.Application
import ke.co.hakicheck.data.local.HakiCheckDatabase
import ke.co.hakicheck.data.repository.FactCheckRepository
import ke.co.hakicheck.data.repository.SettingsRepository

class HakiCheckApplication : Application() {

    val database by lazy { HakiCheckDatabase.getDatabase(this) }
    val settingsRepository by lazy { SettingsRepository(this) }
    val factCheckRepository by lazy { FactCheckRepository(database.savedClaimDao(), settingsRepository) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: HakiCheckApplication
            private set
    }
}
