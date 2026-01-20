import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../global.service";
import {TicketService} from "./ticket.service";
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {NavigateDirective} from "../navigate.directive";

@Component({
	selector: 'app-ticket',
	imports: [
		NgIf,
		NavigateDirective
	],
	templateUrl: './ticket.component.html',
	standalone: true
})

export class TicketComponent implements OnInit {

	tickets: any[] = [];

	constructor(
		private global: GlobalService,
		private ticketService: TicketService,
		private router: Router,
	) {
	}

	get role(){
		return this.global.role;
	}

	ngOnInit(): void {
		if (this.role === 'NOT') this.router.navigate(['/login']);

		this.ticketService.ticketSubject.subscribe(value => {
			this.tickets = value.tickets;
		})
		this.ticketService.findAll();
	}

}
