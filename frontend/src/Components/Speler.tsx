import {useParams} from "react-router-dom";
import React from "react";
import axios from "axios";

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


const Speler = () => {
    let params = useParams();

    const getSpelerURL = "http://localhost:8080/api/spelers/" + params.id;

    const [speler, setSpeler] = React.useState<Speler[]>([]);

    // get spelers from api
    React.useEffect(() => {
        axios.get<Speler[]>(getSpelerURL, {
            auth: {
                username: 'manager',
                password: 'manager'
            }
        }).then((response) =>{
            console.log(response.data);
            setSpeler(response.data);
        });
    }, []);

    return (
            <>
                <div>
                    <h1> </h1>
                    <p>hallo test {params.id}</p>
                </div>
            </>
        )
}

export default Speler