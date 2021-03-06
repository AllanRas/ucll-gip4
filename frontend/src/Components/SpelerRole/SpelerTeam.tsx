import {Link, useNavigate, useParams} from "react-router-dom";
import React, {useEffect} from "react";
import axios from "axios";
import {Button, Container, Table} from "react-bootstrap";
import moment from "moment";

interface ITeam {
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
    spelerDTO : SpelerDTO[]
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

interface Match {
    id : number
    teamBlue : ITeam
    teamRed : ITeam
    scoreBlueTeam : number
    scoreRedTeam : number
    datumtijd: Date
}

const SpelerTeam = () => {
    let params = useParams();
    let navigate = useNavigate();

    const getTeamURL = "http://localhost:8080/api/teams/" + params.id + "/getOne";
    const getSpelersURL = "http://localhost:8080/api/teams/" + params.id +"/spelers";
    const [spelers, setSpelers] = React.useState<Speler[]>([]);

    const [team, setTeam] = React.useState<ITeam>({
        actief: false,
        id: 0,
        managerDTO: {id: 0, userDTO: {achternaam: "", email: "", id: 0, role: "", username: "", voornaam: ""}},
        naam: "",
        spelerDTO: []
    });

    const [matches, setMatches] = React.useState<Match[]>([]);
    const getMatchURL = "http://localhost:8080/api/matches/matchstats/" + params.id;

    useEffect(() => {
        axios.get<ITeam>(getTeamURL, {
            withCredentials : true
        }).then((response) =>{
            setTeam(response.data);
        });

        axios.get<Match[]>(getMatchURL, {
            withCredentials: true
        }).then((response) =>{
            setMatches(response.data);
        }).catch((e) => {
            console.log(e);
        });
        getSpelers();
    }, [getTeamURL]);

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
            <Container className="col-11 bg-dark text-white-50" >
                <div>
                    <h1>{team.naam}</h1>
                    <h2>Manager: {team.managerDTO.userDTO.username}</h2>
                    <br/>
                    <Button onClick={() => navigate(-1)}>
                        Terug
                    </Button>
                </div>
                <br/>
                <h2>Spelers :</h2>
                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                    </tr>
                    </thead>
                    <tbody>
                    {spelers.map(speler => (team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id && !spelerDTO.reserve) ?
                        <tr key={speler.id}>
                            <td>{speler.userDTO.username}</td>
                            <td>{speler.userDTO.voornaam}</td>
                            <td>{speler.userDTO.achternaam}</td>
                        </tr> : null))}
                    </tbody>
                </Table>
                <h2>Reserve spelers :</h2>
                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                    </tr>
                    </thead>
                    <tbody>
                    {spelers.map(speler => (team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id && spelerDTO.reserve) ?
                        <tr key={speler.id}>
                            <td>{speler.userDTO.username}</td>
                            <td>{speler.userDTO.voornaam}</td>
                            <td>{speler.userDTO.achternaam}</td>
                        </tr> : null))}
                    </tbody>
                </Table>
                <h1>Matches</h1>
                <br/>
                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th className="col-md-3">Team Blue</th>
                        <th className="col-md-3">Team Red</th>
                        <th>won</th>
                        <th className="col-md-0">blue - red</th>
                        <th className="col-md-2">datum en tijd</th>
                        <th className="col-md-2"/>
                    </tr>
                    </thead>
                    <tbody>
                    {matches.map(match => (
                        <tr key={match.id}>
                            <td>{match.teamBlue.naam}</td>
                            <td>{match.teamRed.naam}</td>
                            <td>{match.scoreRedTeam === 0 && match.scoreBlueTeam === 0 ? "---": match.scoreRedTeam < match.scoreBlueTeam ? match.teamBlue.naam : match.teamRed.naam}</td>
                            <td>{match.scoreBlueTeam}-{match.scoreRedTeam}</td>
                            <td>{moment(match.datumtijd).format('DD-MM-YYYY hh:mm')}</td>
                            <td><Link to={"/SpelerMatches/" + match.id}>Details & Score</Link></td>
                        </tr>))}
                    </tbody>
                </Table>
            </Container>
        </>
    )
}

export default SpelerTeam