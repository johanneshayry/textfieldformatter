import {
  DelimiterType,
  formatCreditCard,
  getCreditCardType,
  unformatCreditCard
} from 'cleave-zen';
import { registerCursorTracker, DefaultCreditCardDelimiter } from 'cleave-zen';

const getDelimiterRegexByDelimiter = (delimiter: string): RegExp =>
    new RegExp(delimiter.replace(/([.?*+^$[\]\\(){}|-])/g, '\\$1'), 'g')

const stripDelimiters = (
                                  value : string,
                                  current : DelimiterType): string => {
    current.split('').forEach(letter => {
      value = value.replace(getDelimiterRegexByDelimiter(letter), '')
    })

  return value
}
/**
 * `textfield-formatter` Polymer 2 Web Component wrapper for Cleave.js
 *
 * @customElement
 * @polymer
 * @demo demo/index.html
 */
class TextfieldFormatter extends HTMLElement {
  static get is() { return 'textfield-formatter'; }
  static get properties() {
    return {
      conf: {
        type: Object,
        observer: '_confChanged'
      },

    };
  }

  connectedCallback() {
    let el = this.parentElement!.shadowRoot!.querySelector('input'); // retrocompatibility purposes
    if(!el) el = this.parentElement!.querySelector('input');
    el!.addEventListener('input', this.inputChanged(el!));
    registerCursorTracker({ input: el!, delimiter: DefaultCreditCardDelimiter });
  }

  inputChanged(el: HTMLInputElement) {
    return (e: Event) => {
      const value = (e.target as HTMLInputElement).value;
      const creditCardValue = formatCreditCard(value)
      const creditCardType = getCreditCardType(value);
      (this as any).$server.onCreditCardChanged(creditCardType);
      debugger;
      el.value = creditCardValue;
      // should work specifically for the credit card
      if (stripDelimiters(value, DefaultCreditCardDelimiter) !== unformatCreditCard(creditCardValue)) {
        console.error('Value has been striped');
      }
    };
  }

  disconnectedCallback() {
  }
}

window.customElements.define(TextfieldFormatter.is, TextfieldFormatter);
