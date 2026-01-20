import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "../global.service";

@Injectable({
	providedIn: 'root'
})

export class NotificationService {

	notificationSubject = new BehaviorSubject<any>({
		notifications: [],
	});

	constructor(
		private http: HttpClient,
		private global: GlobalService,
	) {
	}

	private get url() {
		return this.global.backendURL + '/notifications'
	}

	findAll() {
		this.http.get(
			this.url,
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => this.notificationSubject.next({
				...this.notificationSubject.value,
				notifications: res.data,
			}),
			error: (e: any) => this.global.error(e),
		})
	}

}
