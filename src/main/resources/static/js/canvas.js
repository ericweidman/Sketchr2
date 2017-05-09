//Establishes the canvas
var canvasID = document.getElementById('canvasID');
canvas = document.createElement('canvas');
canvas.setAttribute('width', '700');
canvas.setAttribute('height', '585');
canvas.setAttribute('id', 'canvas');
canvasID.appendChild(canvas);
if (typeof G_vmlCanvasManager != 'undefined') {
  canvas = G_vmlCanvasManager.initElement(canvas);
}
context = canvas.getContext('2d');
var clickX = new Array();
var clickY = new Array();
var clickDrag = new Array();
var paint;
var currentSize = 5;
var lineSize = new Array();
var current = '#000000';
var clickColor = new Array();

var clickTool = new Array();
var curTool = 'marker';
var marker = "marker";
var eraser = "eraser";
var spray = 'spray';

function addClick(x, y, dragging) {
  clickX.push(x);
  clickY.push(y);
  clickDrag.push(dragging);
  if (curTool == "eraser") {
    clickColor.push("white");
  } else {
    clickColor.push(current);
  }
  lineSize.push(currentSize);
}

function changeBlur() {
  if (curTool == 'spray') {
    context.shadowBlur = 40;
    context.shadowColor = current;
    console.log('this is spray color', current);
  } else {
    context.shadowBlur = 0;
    context.shadowColor = current;
  }
}

function draw() {
  context.lineJoin = 'round';
  var j = clickY.length - 1
  var i = clickX.length - 1;
  /////code by ZACH ⚔ ^//
  if (curTool == "crayon") {
    context.globalAlpha = 0.3;
    context.drawImage(crayonImg, clickX[i], clickY[j], currentSize, currentSize)
  } else {
    context.beginPath();
    if (clickDrag[i] && i) {
      context.moveTo(clickX[i - 1], clickY[i - 1]);
    } else {
      context.moveTo(clickX[i] - 1, clickY[i]);
    }
    context.lineTo(clickX[i], clickY[i]);
    context.closePath();
    context.strokeStyle = clickColor[i];
    context.lineWidth = lineSize[i];
    context.stroke();
  }
  context.globalAlpha = curVal; // Transparency

}

var curVal = context.globalAlpha = 1;

$('#canvas').mousedown(function(e){
  var moveX = e.pageX - this.offsetLeft;
  var moveY = e.pageY - this.offsetTop;
  paint = true;
  addClick(e.pageX - this.offsetLeft, e.pageY - this.offsetTop);
  draw();
});
//moving the mouse after clicked
$('#canvas').mousemove(function(e){
  if(paint){
    addClick(e.pageX - this.offsetLeft, e.pageY - this.offsetTop, true);
    draw();

  }
});
//after the mouse button is released
$('#canvas').mouseup(function(e){
  paint=false;
  contextPush();
});
//when the mouse leaves the canvas
$('#canvas').mouseleave(function(e){
  paint=false;
});

 document.getElementById('clear').addEventListener('click', function() {
 context.clearRect(0,0,canvas.width,canvas.height);
 clickX = [];
 clickY = [];
 clickTool = [];
 clickColor = [];
 clickDrag = [];
 lineSize = []

 });

 //UNDO

var contextPushArray = [];
var contextStep= -1;

function contextPush(){
contextStep++;
if (contextStep < contextPushArray.length){
  contextPushArray.length= contextStep;
}
contextPushArray.push(document.getElementById('canvas').toDataURL());
}
contextPush();

 function undo(){
  if (contextStep > 0){
    contextStep --;
    var canvasImg = new Image();
    canvasImg.src = contextPushArray[contextStep];
    canvasImg.onload = function(){
      context.clearRect(0,0,canvas.width,canvas.height);
      context.drawImage(canvasImg,0,0, canvas.width, canvas.height);
    };
  }

 }
 $('#undo').click('click',function() {
   undo();
});

$('#spray').click('click',function(){
   curTool='spray';
   changeBlur();
 });
 document.getElementById('eraser').addEventListener('click',function(){
   curTool='eraser';
   changeBlur();
 });
 document.getElementById('marker').addEventListener('click',function(){
   curTool='marker';
   changeBlur();
 });

 $('#colorPick').on('change', function(){
   current=this.value;
   context.shadowColor=this.value;
   console.log(current);
 });
 $('#slider').on('input', function(){
   currentSize=this.value;
   console.log(this.value);
 });

///////
$('.opacity').on('input', function(){
  curVal=this.value;
  console.log(this.value);
});
