import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import axios from "axios";


interface Team {
    id : number
    naam : string
    actief : boolean
    spelerDTO : SpelerDTO[]
    managerDTO : {
        id: number
        userDTO: {
            username: string
        }
    }
}

interface SpelerDTO{
    id : number
    reserve : boolean
    spelerid: number
}

interface Speler {
    id: number;
    actief: boolean;
    adresDTO: {
        id: number;
        gemeente: string;
        straat: string;
        huisnummer: string;
        postcode:string;
    }
    geboortedatum: string;
    userDTO: {
        id: number;
        achternaam: string;
        email: string;
        role: string;
        username: string;
        voornaam: string;
    }
}

const AddMatch = () => {


    //get all teams
    const getAllTeamsURL = "http://localhost:8080/api/teams/";
    const [allTeams, setAllTeams]   = useState<Team>({
        actief: false, id: 0, managerDTO: {id: 0, userDTO: {username: ""}}, naam: "", spelerDTO: []
    })

    //set team blue
    const [teamBlue, setTeamBlue] = useState<Team>({
            managerDTO: {id: 0, userDTO: {username: ""}},
            spelerDTO: [],
            actief: false,
            naam: "",
            id: 0
        }
    );

    //set team red
    const [teamRed, setTeamRed] = useState<Team>({
            managerDTO: {id: 0, userDTO: {username: ""}},
            spelerDTO: [],
            actief: false,
            naam: "",
            id: 0
        }
    );


    //get all spelers
    const getSpelersURL = "http://localhost:8080/api/spelers";
    const [spelers, setSpelers] = React.useState<Speler[]>([]);


    useEffect(() => {
        getAllTeams()
    },[]);


    //get all teams api
    const getAllTeams = () => {
        axios.get<Team>(getAllTeamsURL, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            setAllTeams(response.data);
        }).catch(err => {
            console.log(err);
        });
    }

    // get Team blue api
    const getTeamBlue = (teamId : number) => {
        const getTeamURL = "http://localhost:8080/api/teams/" + teamId + "/getOne";
        axios.get<Team>(getTeamURL, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            setTeamBlue(response.data);
        }).catch(err => {
            console.log(err);
        });
    }

    // get Team red api
    const getTeamRed = (teamId : number) => {
        const getTeamURL = "http://localhost:8080/api/teams/" + teamId + "/getOne";
        axios.get<Team>(getTeamURL, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            setTeamRed(response.data);
        }).catch(err => {
            console.log(err);
        });
    }

    const getSpelers = () => {
        axios.get<Speler[]>(getSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            setSpelers(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }

    return (
        <>
            
        </>
    )

}

export default AddMatch