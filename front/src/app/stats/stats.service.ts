import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GlobalService} from "../global.service";

@Injectable({
	providedIn: 'root'
})
export class StatsService {

	constructor(
		private http: HttpClient,
		private global: GlobalService
	) {
	}

	private get url() {
		return this.global.backendURL + '/stats'
	}

	stats() {
		return this.http.get(
			this.url + '/stats',
			{headers: this.global.headersToken}
		);
	}

}
