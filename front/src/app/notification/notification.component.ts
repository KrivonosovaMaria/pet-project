import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {GlobalService} from "../global.service";
import {NotificationService} from "./notification.service";
import {NavigateDirective} from "../navigate.directive";

@Component({
	selector: 'app-notification',
	imports: [
		NavigateDirective,
	],
	templateUrl: './notification.component.html',
	standalone: true
})

export class NotificationComponent implements OnInit {

	notifications: any[] = [];

	constructor(
		private router: Router,
		private global: GlobalService,
		private notificationService: NotificationService,
	) {
	}

	ngOnInit(): void {
		if (this.global.role !== 'USER') this.router.navigate(['/login']);

		this.notificationService.notificationSubject.subscribe(value => {
			this.notifications = value.notifications;
		})
		this.notificationService.findAll();
	}

}
