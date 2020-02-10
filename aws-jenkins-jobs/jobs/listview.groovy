listView('Demo'+ DemoNumber) {
          description('')
          jobs {
            regex('.*'+DemoNumber+'.*')
          }
          columns {
            status()
            weather()
            name()
            lastSuccess()
            lastFailure()
            lastDuration()
            buildButton()
          }
        }