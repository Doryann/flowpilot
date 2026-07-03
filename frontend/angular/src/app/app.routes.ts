import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import {About} from './pages/about/about';
import {Contact} from './pages/contact/contact';
import {Project} from './pages/project/project';
import {Stack} from './pages/stack/stack';
import {Angular} from './pages/stack/angular/angular.component';
import {Springboot} from './pages/stack/springboot/springboot.component';

export const routes: Routes = [
  {
    path: '',
    component: Home,
  },
  {
    path: 'about',
    component: About
  },
  {
    path: 'contact',
    component: Contact
  },
  {
    path: 'projects',
    component: Project
  },
  {
    path: 'stack',
    children: [
      {
        path: '',
        component: Stack
      },
      {
        path: 'angular',
        component: Angular
      },
      {
        path: 'springboot',
        component: Springboot
      }
    ]
  }
];
