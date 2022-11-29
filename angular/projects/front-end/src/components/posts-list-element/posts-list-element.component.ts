import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-posts-list-element',
  templateUrl: './posts-list-element.component.html',
  styleUrls: ['./posts-list-element.component.scss']
})
export class PostsListElementComponent {
  constructor() {}

  public onMouseMove(event: MouseEvent, target: HTMLDivElement): void {
    let rect = target.getBoundingClientRect();
    let x = event.clientX - rect.left;
    let y = event.clientY - rect.top;
    target.style.setProperty('--x', x + 'px');
    target.style.setProperty('--y', y + 'px');
  }
}
