import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect} from "react";
import axios from "axios";
import moment from 'moment';
import {Button, Container} from "react-bootstrap";

interface Team {
    id : number
    naam : string
    actief : boolean
    managerDTO : {
        id: number
        userDTO: {
            achternaam: string
            email: string
            id: number
            role: string
            username: string
            voornaam: string
        }

    }
    spelers : spelers[]
    match : match[]

}

interface spelers {

}

interface match {

}



const Team = () => {
    let params = useParams();
    let navigate = useNavigate();

    const getSpelerURL = "http://localhost:8080/api/teams/" + params.id + "/getOne";

    const [team, setTeam] = React.useState<Team>({
        actief: false,
        id: 0,
        managerDTO: {id: 0, userDTO: {achternaam: "", email: "", id: 0, role: "", username: "", voornaam: ""}},
        match: [],
        naam: "",
        spelers: []
    });

    useEffect(() => {
        axios.get<Team>(getSpelerURL, {
            withCredentials : true
        }).then((response) =>{
            console.log(response.data);
            setTeam(response.data);
        });
    }, [getSpelerURL]);

    return (
        <>
            <Container className="col-8 bg-dark text-white-50" >
                <div>
                    <h1>{team.naam}</h1>

                    <h2>Manager: {team.managerDTO.userDTO.username}</h2>
                    <h2></h2>
                    <br/>
                    <Button onClick={() => navigate("/Teams")}>
                        Terug
                    </Button>
                    <Button onClick={() => navigate("/EditTeam/" + team.id)}>
                        Team naam aanpassen
                    </Button>
                    <Button onClick={() => navigate("/Teams/" + team.id + "/AddSpeler")}>
                        Add speler
                    </Button>
                </div>
            </Container>
        </>
    )
}

export default Team