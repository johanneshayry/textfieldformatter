This branch is a PoC of a migration from cleave.js to `cleavejs-zen`.

Currently only the `CustomCreditCardFieldFormatter` has been tested.
The different configurations have been migrated and should work out of the box.

Not done:
- The addEventListener on the client side is registered only on ConnectCallback. It should be clean up on disconnect.
- A different ValueChangeMode for the TextField are not working well since the value is formatted eagerly and updated to the server side automatically. (if it's not updated automatically with the _onChange then the value is wrong on the server side!).
- The cursor position might be an issue to be tested. It doesn't work for all the different cases (with delimiters)
- An new event with the old and new value must be sent to the server to manage the case of strip (for example copy 1,122121 and it's truncated to 1,12 without warning the user)
- The date, time, numeral, IBAN are not implemented but that should probably mostly a copy/paste of the old Java class
- The current tests can run. They are failing in this version and also in Vaadin 14. The code should be fixed to work (The tests are valid)