var first = "FIRST! ";

//var canvas = Raphael(document.getElementById("canvas"), 700, 700)
var canvas = ""

Raphael.easing_formulas["wiggle"] = function(n) { return Math.random() * 5 };

//TODO: Figure out timing
//TODO: Figure out positioning
//TODO: Animations!
//TODO: Scoring!
//TODO: TODO 

function VilleType(config, elem, callback) {
    canvas = Raphael(elem, 700, 700)
    this.config = config;
    this.questions = config.questions;
    this.taskObjects = [];

    this.callback = callback;

    var that = this
    this.prev = null;

    this.score = 0;

    this.drawNextTimer = 5000; 

    this.ticktock = null;

    this.answer = ""
    this.currentAnswer = "";

    this.timer = config.timer * 20;
    this.totalTime = 0;

    this.difficultyTimer = config.timeToMaxSpeed * 20;
    this.score = 0;
    console.log("Test: " + typeof canvas);

    this.scoreText = canvas.text(130,600, this.score)
    this.scoreText.attr({"fill":"black", "font-size":24});
    console.log("test2");
    this.scoreTitle = canvas.text(50,600, 'Score')
    this.scoreTitle.attr({"fill":"black", "font-size":24});

    this.currentIndex = 0;
    this.answerIndex = 0;

    this.canAnswer = true;
    this.attachKeys();

    console.log(this.config);

    this.lastDelta = window.performance.now();
    this.drawAnswer();
    this.startUp();

}

VilleType.prototype.start = function() {
    var that = this
    var context2D;
    var score = 0;
    var bar = canvas.rect(200,570, that.timer, 20, 5);
  	var timer = that.timer;
  	var multiplier = 1.0;

    that.drawTask();
    that.taskObjects[this.answerIndex].activate()

    //Gameloop
    this.ticktock = setInterval(function() {
        //console.log("Popping");
        var d = that.delta();
        that.totalTime = that.totalTime + 1;
        that.score = that.score + 1;


        //So fugly
        if(that.difficultyTimer/2 == that.totalTime){
        	multiplier = 1.5;
        }
        else if(that.difficultyTimer/4 == that.totalTime){
        	multiplier = 2;
        }
        else if(that.difficultyTimer == that.totalTime){
        	multiplier = 3;
        }

        that.timer = that.timer - multiplier;

        if(that.timer < timer / 4 ){
        	bar.attr({"fill":"red"});
        }
        else if (that.timer < timer / 2){
        	bar.attr({"fill":"yellow"});
        }
        else{
        	bar.attr({"fill":"green"});
        }

        if (that.timer <= 0){
        	//gameOver();
        	that.gameOver();
        }
        else {
        	bar.attr({width:that.timer})
        }

        that.drawNextTimer -= that.delta();
        //console.log(that.drawNextTimer);

        //update(Math.abs(d)) ;

        if (that.drawNextTimer < 0) {
            that.drawTask();
            that.drawNextTimer = 2000;
        }

    }, 50 /*16.6*/)

}

VilleType.prototype.drawTask = function() {
    //var q = this.questions[this.currentIndex]
    var q = this.getQuestion();
    var a = this.getAnswer();
    //this.answer = q.a
    //this.drawAnswer(q.a)

    var t = new taskObject(q, a)

    //Random point on circle
    var angle = Math.random()*Math.PI*2;
    var x = Math.cos(angle)*1500;
    var y = Math.sin(angle)*1500;

    t.setPos(x+400, y+400)

    var el = canvas.circle(0,0, 20);
    el.attr({"fill":"blue"});

    var text = canvas.text(0,0, t.question);
    text.attr({"fill":"white", "font-size":13})

    var ranX = Math.floor(Math.random() * (600 - 70)) + 70;
    var ranY = Math.floor(Math.random() * (400 - 70)) + 70;
    t.addElem(el, 0, 0)
    t.addElem(text, 0, 0)

    var animParams = {cx: ranX+100, cy:ranY+100, 
    x:ranX+100, y:ranY+100,  transform:"s2 2"}
    t.animate(animParams, 300);

    t.set.toBack();
    t.components[0].toBack();

    this.currentIndex += 1;
    this.taskObjects.push(t)
}

VilleType.prototype.stop = function() {
    clearInterval(this.ticktock)
}

VilleType.prototype.delta = function() {
    var dif = window.performance.now() - this.lastDelta
    this.lastDelta = window.performance.now();
    return dif
};

//Each iteration should visit this loop and figure out what to do;
VilleType.prototype.gameLoop = function() {

}

VilleType.prototype.setup = function() {

};

VilleType.prototype.gameOver = function(){
    canvas.clear();
	this.deattachKeys();
	this.stop();
	endText = canvas.text(300,350, 'Final score: ' + this.score);
	endText.attr({"fill":"black", "font-size":44});
	this.callback(this.score);
}


VilleType.prototype.addScore = function(score){
	this.score = this.score + score;
	this.scoreText.remove();
    this.scoreText = canvas.text(130,600, this.score);
	this.scoreText.attr({"fill":"black", "font-size":24});
}

VilleType.prototype.addTime = function(time){
	this.timer = this.timer + time;
	return this.timer;
}

VilleType.prototype.deattachKeys = function(){
	window.onkeypress = null;
};

//Listening for keypresses
VilleType.prototype.attachKeys = function() {
    var that = this
    window.onkeypress = function(e) {
        //console.log(e);
        e.preventDefault();

        var letter = String.fromCharCode(e.keyCode) 
        if (that.canAnswer) {
            that.handleAnswer(letter);
        }
    }
};

VilleType.prototype.handleAnswer = function(key) {
    //console.log(this.currentAnswer.length)
    //console.log(this.getAnswer()[this.currentAnswer.length] + "---" + key)
    if(typeof this.currentAnswer === 'undefined') {
        return;
    }

    if(this.getAnswer()[this.currentAnswer.length] === key) {
        console.log("Correct answer!!")
        this.currentAnswer += key;
        this.taskObjects[this.answerIndex].shake();
    } else {
        this.flashError();
    }
    this.drawAnswer()

    if(this.getAnswer() == this.currentAnswer) {
        console.log("Success");
        
        //var x = randomFloat(100, 400);
        //var y = randomFloat(100, 400);
            
        //createExplosion(70, 70, "#525252");

        this.addTime(10);
        this.addScore(200);

        var ex = this.taskObjects[this.answerIndex].components[0].attrs.cx;
        var ey = this.taskObjects[this.answerIndex].components[0].attrs.cy;
        this.explosion(ex, ey)

        this.currentAnswer = ""
        this.taskObjects[this.answerIndex].hide();
        this.answerIndex += 1;
        if (this.answerIndex==this.currentIndex) {
            console.log("Adding task cause empty")
            this.drawTask();            
        }
        this.drawAnswer()
        this.taskObjects[this.answerIndex].activate();
    }
}

VilleType.prototype.flashError = function() {
    var x = 700/2
    var y = 600

    var width = 90;
    var height = 60;
    var ans = this.getAnswer();

    x = x-(10*ans.length)
    width = width + (25*ans.length)

    var errorBox = canvas.rect(x,y,width, height, 5);
    errorBox.attr({"fill":"red"})
    errorBox.toBack();
    //errorBox.animate({transform:"S1.6"},200,"wiggle",)   
    //errorBox.animate({transform:"S1.5"}, 150, function(e) {
    errorBox.animate({transform:"S1.1"},50,"wiggle", function(e) {
        errorBox.hide();
        errorBox.remove();
    })

    this.addScore(-50);
}

VilleType.prototype.drawAnswer = function() {
    var x = 700/2
    var y = 600

    var width =100;
    var height = 60;

    if(typeof this.answerBox !== 'undefined') {
        this.answerBox.remove();
        this.answerTextElement.remove();
    }

    var text = "_"

    //var ans = this.questions[this.answerIndex].a
    var ans = this.getAnswer();

    x = x-(10*ans.length)
    width = width + (25*ans.length)

    this.answerBox = canvas.rect(x,y,width, height, 5);
    this.answerBox.attr({"fill":"white"})
    text= new Array(ans.length + 1).join(text).split('');

    for (var i = 0; i < this.currentAnswer.length; i++) {
        text[i] = this.currentAnswer[i]
        //console.log(this.currentAnswer[i] + "----" + text[i])
    };
    text = text.join(' ');

    this.answerTextElement = canvas.text(x+(width/2),y+(height/2), text);
    this.answerTextElement.attr({"font-size":42, "font-family":"monospace"})
}

VilleType.prototype.getQuestion = function() {
    var q = this.questions[this.currentIndex].q
    return q;
};

VilleType.prototype.getAnswer = function() {
    var a = this.questions[this.answerIndex].a
    return a;
};
/*
Creates a input box with the right number of blanks
 */
VilleType.prototype.buildInputBox = function() {
    // body...
};

VilleType.prototype.explosion = function(x,y) {
    var elements = new canvas.set();
    var size = Math.floor(Math.random() * (5 - 30)) + 30;

    var px = x;
    var py = y;
    var colour = 0

    for (var i = 0; i < 10; i++) {
        var circle = canvas.circle(px,py, Math.floor(Math.random() * (40 - 5)) + 5)
        circle.attr({
            "fill":"black",
           "stroke-width":0
        })

        if (colour) {
            circle.attr({"fill":"#525252"});
            colour = 0
        }else {
            colour = 1
        }
        elements.push(circle);
        //console.log("Buildiong circles x:" + x + " y: " + y);

        var angle = Math.random()*Math.PI*2;
        var radius = Math.floor(Math.random() * (120 - 20)) + 20;
        var rx = Math.cos(angle)*radius+px;
        var ry = Math.sin(angle)*radius+py;

        console.log("a:" + angle + " r:" + radius + " rx:" + rx)

        circle.animate({cx:rx,cy:ry/*,transform:"S1.3"*/}, 500)
    };

    elements.animate({transform:"S0"}, 500, function() {
        elements.forEach(function(e) {
            e.remove();
        })
    })
}

VilleType.prototype.startUp = function() {
    var that = this;
    var counter = ""

    function timer(){
        count = count-1;
        if (count <= 0){
            clearInterval(counter);
            text = canvas.text(350,300, 'GO!')
            text.attr({"fill":"black", "font-size":56})
            text.animate({y:400, x:350, "fill-opacity":0}, 1000, ">");

            //ville = new VilleType(config)
            clearInterval(counter);

            that.start()
            return;
        }
        text = canvas.text(350,300, count)
        text.attr({"fill":"black", "font-size":56})
        text.animate({y:400, x:350, "fill-opacity":0}, 1000, ">");
    }

    var count = 6;
    counter = setInterval(timer, 1000);

        // body...
};

function taskObject(question, answer) {
    this.question = question;
    this.answer = answer;

    this.x = 0;
    this.y = 0;

    this.components = [];
    this.set = new canvas.set();
    this.shaking = false;
}
 
taskObject.prototype.setPos = function(x, y) {
    this.x = x;
    this.y = y   
}

taskObject.prototype.addElem = function(elem, posX, posY) {
    this.components.push(elem);
    elem.attr({cx: this.x + posX, cy: this.y+posY});
    elem.attr({x: this.x + posX, y: this.y+posY});
    elem.toFront();

    this.set.push(elem);
}

taskObject.prototype.animate = function(animation, time) {  
    this.set.animate(animation, time, "<");

}

taskObject.prototype.hide = function() {
    this.set.hide()
}

taskObject.prototype.activate = function() {
    this.components[0].attr({fill:"red"})
    this.set.toFront();
    //this.components[0].toFront();
    //this.components[1].toFront();
}

taskObject.prototype.shake = function() {
    var that = this;

    if(!this.shaking) {
        that.shaking = true
        this.set.animate({transform:"T1,1,S2"},200,"wiggle", function(e) {
            that.set.transform("T0,0,s2");
            that.shaking = false;
        })
    }
}

var config = {
    "mode":"type",
    "timeToMaxSpeed":"15",
    "timer":"20",
    "questions": [
        {q:"20+5", a:"25"},
        {q:"90-10", a:"80"},
        {q:"70-5", a:"65"},
        {q:"first", a:"first"},
        {q:"second", a:"second"},
        {q:"third", a:"third"},
        {q:"fourth", a:"fourth"},
        {q:"fifth", a:"fifth"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"},
        {q:"11", a:"11"}
    ]
}
/*
var count=4;
var counter = setInterval(timer, 1000);
/*
function timer(){
	count = count-1;
	if (count <= 0){
		clearInterval(counter);
		text = canvas.text(350,300, 'GO!')
		text.attr({"fill":"black", "font-size":56})
		text.animate({y:400, x:350, "fill-opacity":0}, 1000, ">");
        //ville = new VilleType(config)

        return;
    }
    text = canvas.text(350,300, count)
    text.attr({"fill":"black", "font-size":56})
    text.animate({y:400, x:350, "fill-opacity":0}, 1000, ">");
}*/

//ville = new VilleType(config)

window.addEventListener('keydown', function (e) {
    if(e.keyCode === 8){
        e.preventDefault();
    }
}, true);

