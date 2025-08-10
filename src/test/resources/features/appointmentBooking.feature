@MHRSAppointment
Feature: MHRS Appointment Booking

  @BookAppointment @LoginRequired
    Scenario Outline: Book appointment for a clinic with doctor and time slot
     When User opens appointment flow
     And Select city "<city>"
     And Optionally select district "<district>"
     And Select clinic "<clinic>"
     And Optionally select hospital "<hospital>"
     And Optionally select exam place "<examPlace>"
     And Optionally select doctor "<doctor>"
     And Select a slot between "<startTime>" and "<endTime>"
     And Confirm appointment
     Then Verify appointment confirmation

    Examples:
      | city     | clinic                       | district | hospital                                   | examPlace | doctor           | startTime | endTime |
      | İstanbul | Dahiliye (İç Hastalıkları)   | SKIP     | SKIP                                       | SKIP      | SKIP             | 09:00     | 12:00   |
      | İstanbul | Dahiliye (İç Hastalıkları)   | Ümraniye | SBÜ Ümraniye Eğitim ve Araş. Hastanesi     | Poliklinik-2 | Dr. Ahmet Örnek | 13:00     | 16:00   |