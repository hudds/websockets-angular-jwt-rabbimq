import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FlashMessageService {

  private messages = new Map()

  constructor() { }

  putMessage(key : string, value : any){
    this.messages.set(key, value);
  }

  getMessage(key : string) {
    let message = this.messages.get(key);
    this.messages.delete(key);
    return message;
  }
}
