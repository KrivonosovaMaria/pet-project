import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {GlobalService} from "../global.service";
import {Router} from "@angular/router";

@Injectable({
	providedIn: 'root'
})

export class RaceCommentService {

	constructor(
		private http: HttpClient,
		private global: GlobalService,
		private router: Router,
	) {
	}

	private get url() {
		return this.global.backendURL + '/races/comments'
	}

	save(comment: any, raceId: number) {
		return this.http.post(
			this.url,
			JSON.stringify(comment),
			{
				headers: this.global.headersJsonToken,
				params: new HttpParams().appendAll({raceId: raceId})
			}
		)
	}

}
