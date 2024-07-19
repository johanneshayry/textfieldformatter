import {
  CursorTrackerDestructor,
  DelimiterType,
  formatCreditCard,
  FormatCreditCardOptions,
  formatDate,
  formatGeneral, formatNumeral,
  formatTime,
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
 * `textfield-formatter` PWeb Component wrapper for Cleavejs Zen
 *
 * @customElement
 */
class TextfieldFormatter extends HTMLElement {
  static get is() { return 'textfield-formatter'; }

  private configuration: any;
  private valueChangeEvent: string = 'input';
  private cursorTracker?: CursorTrackerDestructor;
  private el?: HTMLInputElement | null = null;
  private formatType: 'creditCard' | 'general' | 'numeral' | 'date'  | 'time' = 'creditCard';

  connectedCallback() {
    this.el = this.parentElement!.shadowRoot!.querySelector('input'); // retrocompatibility purposes
    if(!this.el) {
      this.el = this.parentElement!.querySelector('input');
    }
    if (this.el) {
      this.el!.addEventListener('input', this.creditTypeChanged());
      if (['VAADIN-TEXT-FIELD', 'VAADIN-TEXT-AREA'].includes(this.parentElement!.tagName.toUpperCase())) {
        this.el!.addEventListener(this.valueChangeEvent, this.inputChanged());
      } else {
        this.el!.addEventListener('change', this.inputValueChanged());
      }
    }
  }

  creditTypeChanged() {
    return (e: Event) => {
      const value = (e.target as HTMLInputElement).value;
      const creditCardType = getCreditCardType(value);
      (this as any).$server.onCreditCardChanged(creditCardType);
    };
  }

  inputChanged() {
    return (e: Event) => {
      const value = (e.target as HTMLInputElement).value;
      const formattedValue = this.formatValue(value);

      (this.parentElement as any).value = formattedValue;
      if (formattedValue) {
        this.el!.value = formattedValue;
      }
      (this.parentElement as any)._onChange(e);

    };
  }

  formatValue(value: string) {
    let formattedValue;
    switch (this.formatType) {
      case "creditCard":
        formattedValue = formatCreditCard(value, this.configuration);
        break;
      case "general":
        formattedValue = formatGeneral(value, this.configuration);
        break;
      case "numeral":
        formattedValue = formatNumeral(value, this.configuration);
        break;
      case "date":
        formattedValue = formatDate(value, this.configuration);
        break;
      case "time":
        formattedValue = formatTime(value, this.configuration);
        break;
    }
    return formattedValue;
  }

  inputValueChanged() {
    return (e: Event) => {
      const value = (e.target as HTMLInputElement).value;
      const formattedValue = this.formatValue(value);
      (this.parentElement as any).value = formattedValue;
      // should work specifically for the credit card
      if (stripDelimiters(value, DefaultCreditCardDelimiter) !== unformatCreditCard(formattedValue)) {
        console.error('Value has been striped');
      }
    };
  }

  updateConf(configuration: any, formatType: 'creditCard' | 'general' | 'numeral' | 'date'  | 'time') {
    this.formatType = formatType;
    this.configuration = configuration;
    console.error('updateConf ' + this.configuration);
    if (this.cursorTracker) {
      this.cursorTracker()
    }
    this.cursorTracker = registerCursorTracker({ input: this.el!, delimiter: this.configuration.delimiter });
  }
  updateValueChangeEvent(valueChangeEvent: string) {
    this.valueChangeEvent = valueChangeEvent;
    console.error('updateValueChangeEvent' + this.valueChangeEvent);
  }

  disconnectedCallback() {
    if (this.cursorTracker) {
      this.cursorTracker()
    }
  }
}

window.customElements.define(TextfieldFormatter.is, TextfieldFormatter);
