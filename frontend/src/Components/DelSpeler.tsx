import {Link, useParams} from "react-router-dom";
import React from "react";
import axios from "axios";
import moment from 'moment';
import {Button, Container} from "react-bootstrap";

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


const DelSpeler = () => {
    let params = useParams();

    const getSpelerURL = "http://localhost:8080/api/spelers/" + params.id + "/getOne";

    const delSpelerURL = "http://localhost:8080/api/spelers/" + params.id + "/delete";

    const [speler, setSpeler] = React.useState<Speler | any>(    {
        id: 0,
        actief: false,
        adresDTO: {
            id: 0,
            gemeente:'',
            straat:'',
            huisnummer: '',
            postcode:'',
        },
        geboortedatum: '',
        userDTO: {
            id: 0,
            achternaam: '',
            email: '',
            role: '',
            username: '',
            voornaam: '',
        }
    });

    // get spelers from api
    React.useEffect(() => {
        getSpelers();
    }, []);

    const getSpelers = async () => {
        await axios.get<Speler>(getSpelerURL, {
            auth: {
                username: 'manager',
                password: 'manager'
            }
        }).then((response) =>{
            console.log(response.data);
            setSpeler(response.data);
        })
    }

    const Delete = async () => {
        await axios.put<Speler>(delSpelerURL, {},{
            auth: {
                username: 'manager',
                password: 'manager'
            }
        }).then((response) => {
            getSpelers();
            console.log(response.data)
        })
    }

    return (
        <>
            <Container className="col-5 bg-dark text-white-50">
                <div>
                    <br/>
                    <h1>Speler: {speler.userDTO.username} </h1>
                    <br/>
                    <h3>Voornaam: {speler.userDTO.voornaam} </h3>
                    <h3>Achternaam: {speler.userDTO.achternaam}</h3>
                    <h3>Email: {speler.userDTO.email}</h3>
                    <h3>Geboortedatum: {moment(speler.geboortedatum).format('DD/MM/YYYY')}</h3>
                    <h3>Actief: {speler.actief ? "true": "false"} </h3>
                    <br/>

                    <h3>Adres </h3>
                    <h4>Gemeente: {speler.adresDTO.gemeente}</h4>
                    <h4>Postcode: {speler.adresDTO.postcode}</h4>
                    <h4>Straat: {speler.adresDTO.straat}</h4>
                    <h4>Huisnr.: {speler.adresDTO.huisnummer}</h4>

                    <br/>

                    {speler.actief?
                        <Button variant={"danger"}  onClick={() => Delete()}>
                            deactiveren
                        </Button>
                        :
                        <Button variant={"primary"} onClick={() => Delete()}>
                            Activeren
                        </Button>
                    }

                    <div>
                        <Link to={"/Spelers/" + speler.id}>
                            <Button>
                                Cancel
                            </Button>
                        </Link>
                    </div>
                </div>
            </Container>

        </>
    )
}

export default DelSpeler