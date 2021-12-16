r_val = 2.0;
console.log("load...");
canv = $('.graph_canvas');
GRAPH_COFF = 67;
GRAPH_MOVE = 110;
X_VALUES = ["-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3"];

//Если R был сохранен в памяти, отрисовывание точки предыдущего запроса

/* проверка на валидность введённых значений r*/

function clearCanvals(){
	canv[0].getContext('2d').clearRect( 0, 0, canv.width(), canv.height());
}
function drawPoint(x, y, color){
	if(x > canv.width() || x < -canv.width() || y > canv.height() || y < -canv.height())
		return;
	//Отрисовка пунктирных линий и точки
	let Axes = canv[0].getContext('2d');
	Axes.setLineDash([2, 2]);
	Axes.beginPath();
	Axes.moveTo(x, 110);
	Axes.lineTo(x, y);
	Axes.moveTo(110, y);
	Axes.lineTo(x, y);
	Axes.stroke();
	Axes.fillStyle = color;
	Axes.arc(x, y, 2, 0, 2*Math.PI);
	Axes.fill();
}
function checkTriangle(x, y, r){
	return x <= 0 && y <= 0 && x >= -y - r;
}

function checkRectangle(x, y, r){
	return x >= 0 && y >= 0 && x <= r/2 && y <= r;
}
function checkCircle(x, y, r){
	return x >= 0 && y <= 0 && x*x + y*y <= (r/2)*(r/2);
}function isIn(x, y, r){
	return checkRectangle(x, y, r) || checkTriangle(x, y, r) || checkCircle(x, y, r)
}
//Отрисовка с кординатами пользователя
function redrawFromInput(x, y, r){
	if (isIn(x, y, r_val))
		color="green";
	else
		color="red";
	drawPoint(x * GRAPH_COFF / r_val + GRAPH_MOVE,
		-(y / r_val * GRAPH_COFF - GRAPH_MOVE), color);
}

function drawPointsFromTable(){
	canv = $('.graph_canvas');
	canv.on("click", canv_click);
	clearCanvals();
	let cells = Array.prototype.slice.call(document.getElementById("result-table").getElementsByTagName("td"));
	let n = cells.length;
	if(Number(cells[2].innerHTML) !== 0){
		for(let i = 0; i < n; i=i+5){
			redrawFromInput(Number(cells[i].innerHTML),
				Number(cells[i+1].innerHTML),
				Number(cells[i+2].innerHTML));
		}
	}
}

function canv_click(event){
	console.log("Click!")

	let x = (event.offsetX - GRAPH_MOVE) / GRAPH_COFF * r_val;

	let minDiff = Infinity;
	let nearestXValue;

	for (let i = 0; i < X_VALUES.length; i++){
		if (Math.abs(x - X_VALUES[i]) < minDiff){
			minDiff = Math.abs(x - X_VALUES[i]);
			nearestXValue = X_VALUES[i];
		}
	}

	let y = (-event.offsetY + GRAPH_MOVE) / GRAPH_COFF * r_val;

	if (y < -5) y = -5;
	if (y > 5) y = 5;

	drawPoint(nearestXValue * GRAPH_COFF/r_val + GRAPH_MOVE, -(y/r_val * GRAPH_COFF - GRAPH_MOVE), "red");

	//Выставить Х и У в поле для ввода после нажатия на интерактивную область

	$("#input-form\\:y-text_hinput").attr("value", y.toString().substring(0, 5));

	$("input[type='radio'][value='"+ nearestXValue +"']").click()

	$("#input-form\\:submit_button").click();
}

function setR(){
	r_val = $("#input-form\\:basic_input").attr("aria-valuenow");
	window.localStorage.setItem("r_val", r_val);
}

$(function (){
	r_val = window.localStorage.getItem("r_val");
	if (r_val == null)
		r_val = 2;
	setTimeout(drawPointsFromTable, 500);
})