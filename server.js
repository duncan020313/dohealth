var express = require('express')
const app = express()
const mongoClient = require('mongodb').MongoClient
const url = "mongodb://localhost:27017"
var bodyParser = require('body-parser')
app.use(bodyParser.json());



app.listen(443, function(){
    console.log('Server is running...')
})

 mongoClient.connect(url, (err, client) => {
    if(err) {
        console.log("Error while connecting mongo client")
    }        
    else {
        const myDb = client.db('gohealth')
        const collection_user = myDb.collection('user')
        const collection_report = myDb.collection('report') 
        const collection_group = myDb.collection('GGGGG')
        const group_users = myDb.collection('group_users')

        app.post('/join', (req, res) => {
            const newUser = {
                id : req.body.id,
                nickname: req.body.nickname
            }
            const query = {id : req.body.id}
            collection_user.findOne(query, (err, result) => {
                if (result == null) {
                    collection_user.insertOne(newUser, (err, result) => {
                    })
                } 
            })
        })

        app.post('/report',(req, res) => {
            console.log(req.body.Report)
            var flag = false
            const newReport = {
                id : req.body.id,
                report: [req.body.Report],
                Jan:parseInt(0),
                Feb:parseInt(0),
                Mar:parseInt(0),
                Apr:parseInt(0),
                May:parseInt(0),
                Jun:parseInt(0),
                Jul:parseInt(0),
                Aug:parseInt(0),
                Sep:parseInt(0),
                Oct:parseInt(0),
                Nov:parseInt(0),
                Dec:parseInt(0)
            }

            const query = {id : req.body.id}
            collection_report.find(query).toArray(function(err, result){
                if (result.length == 0) {
                    collection_report.insertOne(newReport, (err, result) => {})
                } 

                else {  
                        for(var i=0; i < result[0].report.length; i++) {
                            arr = result[0].report[i].split('#')

                            if((arr[0] == req.body.date ) & (arr[2]==req.body.name)){
                                result[0].report[i] = req.body.Report
                                console.log(arr[0])
                                flag=true
                                
                                collection_report.updateMany({id: req.body.id}, {$set: {report: result[0].report}}, (err, result) => {})
                                break
                        } 
                    }
                        if(!flag){
                            collection_report.updateMany({id: req.body.id}, {$push: {report: req.body.Report}}, (err, result) => {})  
                            const monthly = arr[0].split('/')
                                switch(monthly) {
                                    case '01':  collection_report.Jan++
                                    case '02':  collection_report.Feb++
                                    case '03':  collection_report.Mar++
                                    case '04':  collection_report.Apr++
                                    case '05':  collection_report.May++
                                    case '06':  collection_report.Jun++
                                    case '07':  collection_report.Jul++
                                    case '08':  collection_report.Aug++
                                    case '09':  collection_report.Sep++
                                    case '10':  collection_report.Oct++
                                    case '11':  collection_report.Nov++
                                    case '12':  collection_report.Dec++
                          } 
                          console.log("---------------------------rangk------------------------")
                          console.log(collection_report.Jul)        
                }
            }
        }) 
    })

        app.post('/joingroup', (req, res) => {
            console.log("---------joingroup-------------")

                group_users.insertOne({[req.body.id]: req.body.groupid}, (err, result) => {
                        res.status(200).send()
                    })  
        })

        app.post('/mygroup',  (req, res) => {
                var grouplist = []
                var returnlist =[]

                group_users.find({}).toArray( function(err, result){
                if(result != null){

                for(var i=0; i<result.length; i++){
                    var re = Object.keys(result[i]).map((key) => [Number(key), result[i][key]]);
                    grouplist.push(re[0][1])

                        collection_group.findOne({groupid : re[0][1]}, (err, result1) =>{
                            //var groupitem = Object.keys(result).map((key) => [Number(key), result[key]]);
                            returnlist.push(result1)
                    })
                }
            }

                //setTimeout(function() { 
                    //res.status(200).send(returnlist)
                    //console.log(returnlist) }, 100);
   
            }
            )
            
        })

        app.post('/creategroup', (req, res) => {
            const newgroup = {
                groupid : req.body.groupid,
                groupname: req.body.groupname,
                groupnumber: req.body.groupnumber,
                groupthreshold:req.body.groupthreshold,
                groupmember :req.body.id
            }

            console.log(newgroup)
            group_users.insertOne({[req.body.id]:req.body.groupid}, (err, result) => {})
            collection_group.insertOne(newgroup, (err, result) => {
                console.log(collection_group.findOne({groupname:'30'},(err, result)=>{
                    console.log(result)

                }
                ))
            })
        })

        app.post('/date', (req, res) => {
            console.log(req.body.id)
            var returnarr = ['none']

            collection_report.find({id: req.body.id}).toArray(function(err, result){
                if(result.length == 0){ res.send(returnarr)}

                else{
                    for(var i=0; i < result[0].report.length; i++) {
                        console.log(result[0].report)
                        arr = result[0].report[i].split('#')


                        if((arr[0] == req.body.date)){
                            returnarr.push(result[0].report[i])
                        }
                    } 
                    console.log(returnarr)
                    res.status(200).send(returnarr)
                }
            })
        })

        app.get('/getgroup', (req, res) => {
            placecollection.findOne({id: req.body.id}).toArray(function(err, result) {
                res.status(200).send(result.participants)
            })
        })

        app.get('/getnickname', (request, response) => {
            collection_user.findOne({id : [request.body.id]}, (error,result)=>{
                if (error) throw error;
                response.json(result.nickname);
            })
         }) 
    }
    })
