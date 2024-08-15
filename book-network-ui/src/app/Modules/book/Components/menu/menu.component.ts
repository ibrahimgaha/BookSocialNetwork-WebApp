import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private router : Router) { }

  ngOnInit(): void {
    const linkColor=document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      if(window.location.href.endsWith,(link.getAttribute('href') ||'')){
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach(link => link.classList.remove('active'));
        link.classList.add('active');
      });
    })
  }

  logOut(){
    localStorage.removeItem('token');
    this.router.navigate(['login'])
  }

}
