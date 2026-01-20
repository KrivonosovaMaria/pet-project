import {Component, Input, OnInit} from '@angular/core';
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-sales-ratio',
	imports: [
		ChartComponent
	],
	templateUrl: './stats-sales-ratio.component.html',
	standalone: true,
})

export class StatsSalesRatioComponent implements OnInit{

	@Input() races: any[] = [];

	chartOptions: any;

	get racesSorted() {
		let res = [...this.races];

		res.sort((a, b) => b.salesRatio - a.salesRatio)

		return res.slice(0, 5);
	}

	ngOnInit(): void {
		setTimeout(() => {
			this.draw()
		}, 500)
	}

	generatePDF() {
		let data: any = document.getElementById('pdf');
		html2canvas(data).then(canvas => {
			const content = canvas.toDataURL('image/png');

			let jsPdf;
			if (canvas.width > canvas.height) {
				jsPdf = new jsPDF('p', 'cm', 'a4');
				jsPdf.addImage(content, 'PNG', 0, 0, 21, 0);
			} else {
				jsPdf = new jsPDF('p', 'pt', [canvas.width, canvas.height]);
				jsPdf.addImage(content, 'PNG', 0, 0, canvas.width, canvas.height);
			}

			jsPdf.save('pdf.pdf');
		});
	}

	draw() {
		let temp = this.racesSorted;
		let names: any[] = [];
		let values: any[] = [];
		for (let i = 0; i < temp.length; i++) {
			names[i] = temp[i].name;
			values[i] = temp[i].salesRatio;
		}
		this.chartOptions = {
			series: [
				{
					name: "Коэффициент продаж",
					data: values
				}
			],
			chart: {
				height: 300,
				type: "bar",
			},
			colors: [
				"#008FFB",
				"#00E396",
				"#FEB019",
				"#FF4560",
				"#775DD0",
				"#546E7A",
				"#26a69a",
				"#D10CE8"
			],
			plotOptions: {
				bar: {
					columnWidth: "45%",
					distributed: true
				}
			},
			dataLabels: {
				enabled: false
			},
			legend: {
				show: false
			},
			grid: {
				show: false
			},
			xaxis: {
				categories: names,
				labels: {
					style: {
						colors: [
							"#008FFB",
							"#00E396",
							"#FEB019",
							"#FF4560",
							"#775DD0",
							"#546E7A",
							"#26a69a",
							"#D10CE8"
						],
						fontSize: "12px"
					}
				}
			}
		};
	}

}
