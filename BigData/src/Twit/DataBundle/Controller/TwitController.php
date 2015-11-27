<?php

namespace Twit\DataBundle\Controller;

use Twit\DataBundle\Document\hashtag;
use Twit\DataBundle\Document\twit_colec;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Process\Process;
use Symfony\Component\HttpFoundation\Request;


class TwitController extends Controller
{
    public function indexAction(Request $request)
    {
        $hashtag = new hashtag();
        $formBuilder = $this->get('form.factory')->createBuilder('form', $hashtag);
        $formBuilder->add('hashtag', 'text')->add('langue', 'language')->add('avec_langue', 'checkbox', array('required' => false))->add('submit', 'submit');
        $form = $formBuilder->getForm();
        $repository = $this->get('doctrine_mongodb')->getRepository('TwitDataBundle:twit_colec');
        $twits = $repository->findAll();
        $dm = $this->get('doctrine_mongodb')->getManager();
        foreach ($twits as $twit)
        {
            $dm->remove($twit);
        }
        $dm->flush();
        $form->handleRequest($request);
        if ($form->isValid()) {
            if ($form->get("avec_langue")->getData() == true) {
                $process = new Process('java -jar /home/crea/java/twitdata/twitdata.jar "' . $form->get("hashtag")->getData() . '" ' . $form->get('langue')->getData());
            }
            else {
                $process = new Process('java -jar /home/crea/java/twitdata/twitdata.jar "' . $form->get("hashtag")->getData() . '"');
            }
            $process->run();
        }
        $twits = $repository->findAll();
        return $this->render('TwitDataBundle:Twit:index.html.twig', array('twits' => $twits, 'form' => $form->createView(), 'post' => $request->getMethod(),));
    }
}
