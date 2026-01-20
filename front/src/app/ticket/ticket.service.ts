import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalService} from "../global.service";

@Injectable({
	providedIn: 'root'
})
export class TicketService {

	ticketSubject = new BehaviorSubject<any>({
		tickets: [],
	});

	constructor(
		private http: HttpClient,
		private global: GlobalService,
	) {
	}

	private get url() {
		return this.global.backendURL + '/tickets'
	}

	findAll() {
		this.http.get(
			this.url,
			{headers: this.global.headersToken}
		).subscribe({
			next: (res: any) => this.ticketSubject.next({
				...this.ticketSubject.value,
				tickets: res.data,
			}),
			error: (e: any) => this.global.error(e),
		})
	}

	save(count: number, raceId: number) {
		return this.http.post(
			this.url,
			"",
			{
				headers: this.global.headersJsonToken,
				params: new HttpParams().appendAll({
					count: count,
					raceId: raceId,
				}),
			}
		)
	}

}
