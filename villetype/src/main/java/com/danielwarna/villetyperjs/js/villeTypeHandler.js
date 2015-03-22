 com_danielwarna_villetyperjs_JsVilleType = function() {
   var that, callback, slice = Array.prototype.slice;
   that = this;

  // Sovelluksessa käytettävä callback-funktio:
  callback = function(score) {
    // sendResults määritellään myöhemmin JsCards-luokassa
    //that.sendResults(slice.apply(arguments));
    console.log("#######\nSENDING SCORE\n" + score);
    that.sendResults({"sc":score});
  };

  // Suorittaa cards-funktion annetuilla parametreilla:
  // HTML-elementti, data-objekti, pisteytystieto ja callback-funktio
  //cards(this.getElement(),  this.getState().questions,    this.getState().pointsPerCalc, callback);
    var config = this.getState().jsonConfig
    console.log("Teststring " + this.getState().testString);
    console.log("Config " + config);
    //console.log(JSON.parse(config))

    var config2 = {
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
    console.log("\n\n\n ############\n#############\nRunning VilleType")

    console.log(config.questions.myArrayList);
    config.questions = config.questions.myArrayList;
    var elem = this.getElement()

    var qArray = []
    console.log(config)
    for(var i = 0; i<config.questions.length; i++) {
        //config.questions[i]=config.questions[i].myHashMap;
        console.log(config.questions[i].myHashMap)
        qArray.push(config.questions[i].myHashMap);
    }
    config.questions = qArray;
    console.log(config);

    ville = new VilleType(config, elem, callback);

};