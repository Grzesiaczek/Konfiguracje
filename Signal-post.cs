﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Brain3D
{
    class Signal : DrawableComposite
    {
        Vector3 source;
        Vector3 target;

        Vector3 vector;
        Vector3 bullet;

        Pipe pipe;

        double factor;
        bool active;

        public Signal(Vector3 source, Vector3 target)
        {
            this.source = source;
            this.target = target;

            pipe = new Pipe(source, source, 0.5f, 0.1f, 1);
            pipe.Color = Color.Khaki;

            shift();
        }

        void shift()
        {
            vector = target - source;
            bullet = vector;
            bullet.Normalize();
            bullet *= 1.6f;
            vector -= bullet / 2;
        }

        void activate()
        {
            pipe.Scale = 1;
            pipe.show();
            active = true;
        }

        public override void show()
        {
            pipe.add();
        }

        public override void hide()
        {
            pipe.hide();
            active = false;
        }

        public Vector3 Source
        {
            set
            {
                source = value;
            }
        }

        public Vector3 Target
        {
            set
            {
                target = value;
            }
        }
    }
}
