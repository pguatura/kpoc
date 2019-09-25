//
//  ViewController.swift
//  iosApp
//
//  Created by Paula Guatura on 25/09/19.
//  Copyright © 2019 Paula Guatura. All rights reserved.
//

import UIKit
import api

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 21))
        label.center = CGPoint(x: 160, y: 285)
        label.textAlignment = .center
        label.font = label.font.withSize(25)
        label.text = CommonKt.createApplicationScreenMessage()
        view.addSubview(label)
    }


}

